<?php

namespace User\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\Validator\Between;
use Zend\Validator\Digits;
use Zend\Validator\ValidatorChain;
use Zend\View\Model\JsonModel;

class UserController extends AbstractActionController
{

 /**
  * @var  \Facebook $facebook
  */
 protected $facebook = null;

 protected $fb_id = null;

 /**
  * @var \User\Service\UserTable
  */
 protected $userTable = null;

 public function AuthenticateAction()
 {
  $success = false;
  $message = "";

  if (!$this->getFbId())
   return new JsonModel(array(
    'success' => false,
    'message' => "Facebook not connected",
   ));

  //Check if the user password expired
  if ($this->getUserTable()->getUser()->getIncorrectPasswordAttempts() > 2)
   return new JsonModel(array('success' => false, 'message' => 'Too many login attempts'));

  //match the password
  if ($this->getUserTable()->authenticate($this->params()->fromQuery('password')))
   	//sweet, we're in
  	return new JsonModel(array('success' => true));
  else
   //bad, user.  slap on the hand.
   return new JsonModel(array(
    'success' => false,
    'message' => "Could not authenticate user",
   ));

 }

 public function GetQuestionsAction()
 {
  // dump the preset questions to the JSON response
  return new JsonModel(array('success' => true, 'questions' => $this->getUserTable()->getQuestions()));
 }

 public function SetQuestionAction()
 {
  $question = $this->params()->fromQuery('question');
  $password = $this->params()->fromQuery('password');

  //validate that the question set matches something in the array
  $validator_chain = new ValidatorChain();
  $validator_chain->attach(new Digits());
  $validator_chain->attach(new \Zend\Validator\Between(array('inclusive' => true, 'min' => 0, 'max' => 2)));

  //Check if the user password expired
  if ($this->getUserTable()->getUser()->getIncorrectPasswordAttempts() > 2)
  	return new JsonModel(array('success' => false, 'message' => 'Too many login attempts'));
  
  //dump the error messages to the json array if the question wasn't in the array
  if (!$validator_chain->isValid($question))
   return new JsonModel(array('success' => false, 'messages' => $validator_chain->getMessages()));

  //check the password matched
  if (!$this->getUserTable()->authenticate($password))
   return new JsonModel(array('success' => false, 'message' => 'Password mismatch'));

  //store the lower case version of the password
  $this->getUserTable()->setQuestion(strtolower($question));

  return new JsonModel(array('success' => true));
 }

 public function GetUserAction()
 {
  //get the user singleton and dump the fields to JSON
  $user = $this->getUserTable()->getUser();

  return new JsonModel(array('success' => true, 'user' => $user->toArray()));
 }

 public function GetFriendsAction()
 {
  $friends = $this->getUserTable()->getFriends();

  return new JsonModel(array('success' => true, 'friends' => $friends));
 }

 public function GetAllFbUsersAction()
 {
  $fb_users = $this->getUserTable()->getAllFbUsers();

  return new JsonModel(array('success' => true, 'fb_users' => $fb_users));
 }

 public function PostMessageAction()
 {
  $message = $this->params()->fromQuery('message');

  $this->getFacebook()->api('/me/feed', 'POST', array(
   'message' => $message
  ));

  return new JsonModel(array('success' => true));
 }

 public function ChangeSkinAction()
 {
  $skin = $this->params()->fromQuery('skin');
  $skin_validator_chain = new ValidatorChain();

  $skin_validator_chain->attach(new Digits());
  $skin_validator_chain->attach(new Between(array('inclusive' => true, 'min' => 0, 'max' => 2)));

  if(!$skin_validator_chain->isValid($skin))
   return new JsonModel(array('success' => false, 'messages' => $skin_validator_chain->getMessages()));

  $this->getUserTable()->setSkin($skin);

  return new JsonModel(array('success' => true));
 }

 public function ResetPasswordAction()
 {
  $answer = $this->params()->fromQuery('answer');
  $new_password = $this->params()->fromQuery('new_password');
  $user = $this->getUserTable()->getUser();

  //get the password validator
  $validator = $user->getPasswordValidator();

  //check the lower case answer given is the same ast he actual lower case answer
  if (strtolower($this->getUserTable()->getUser()->getAnswer()) != strtolower($answer))
  {
   $this->getUserTable()->setPassword('cs2212');
   return new JsonModel(array('success' => false, 'message' => 'Could not verify answer, reset to default'));
  }

  //validate the answer
  if(!$validator->isValid($new_password))
   return new JsonModel(array('success' => false, 'messages' => $validator->getMessages()));

  //set the answer
  $this->getUserTable()->setPassword($new_password);

  return new JsonModel(array('success' => true));
 }

 public function SetAnswerAction()
 {
  $answer = $this->params()->fromQuery('answer');
  $password = $this->params()->fromQuery('password');

  //we only need this validator once
  $validator_chain = new ValidatorChain();
  $validator_chain->attach(new \Zend\Validator\StringLength(array('min' => 1, 'max' => 30)));

  //Check if the user password expired
  if ($this->getUserTable()->getUser()->getIncorrectPasswordAttempts() > 2)
  	return new JsonModel(array('success' => false, 'message' => 'Too many login attempts'));
  
  //validate the answer
  if(!$validator_chain->isValid($answer))
   return new JsonModel(array('success' => false, 'messages' => $validator_chain->getMessages()));

  //verify the password
  if (!$this->getUserTable()->authenticate($password))
   return new JsonModel(array('success' => false, 'message' => 'Password mismatch'));

  //set the answer
  $this->getUserTable()->setAnswer($answer);

  return new JsonModel(array('success' => true));
 }

 public function GetQuestionIndexAction()
 {
  return new JsonModel(array('success' => true, 'question_index' => $this->getUserTable()->getUser()->getQuestion()));
 }

 public function SetPasswordAction()
 {
  $user = $this->getUserTable()->getUser();
  $old_password = $this->params()->fromQuery('old_password');
  $new_password = $this->params()->fromQuery('new_password');
  $validator = $user->getPasswordValidator();

  //Check if the user password expired
  if ($this->getUserTable()->getUser()->getIncorrectPasswordAttempts() > 2)
  	return new JsonModel(array('success' => false, 'message' => 'Too many login attempts'));
  
  //validate the password
  if (!$validator->isValid($new_password))
   return new JsonModel(array('success' => false, 'messages' => $validator->getMessages()));

  //check the old password was correct
  if(!$this->getUserTable()->authenticate($old_password))
   return new JsonModel(array('success' => false, 'message' => 'Password mismatch'));

  //set the new password
  $this->getUserTable()->setPassword($new_password);

  return new JsonModel(array('success' => true));
 }

 /**
  * Return a Facebook object
  * @return \Facebook
  */
 public function getFacebook()
 {
  if ($this->facebook) return $this->facebook;
  $this->facebook = $this->getServiceLocator()->get('Application\Service\Facebook');
  return $this->facebook;
 }

 public function getFbId()
 {
  if ($this->fb_id) return $this->fb_id;
  $this->fb_id = $this->getServiceLocator()->get('Application\Service\FbId');
  return $this->fb_id;
 }

 /**
  * @return \User\Service\UserTable
  */
 public function getUserTable()
 {
  if ($this->userTable) return $this->userTable;
  $this->userTable = $this->getServiceLocator()->get('User\Service\UserTable');
  return $this->userTable;
 }


}

