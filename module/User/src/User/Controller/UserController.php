<?php

namespace User\Controller;

use User\Entity\User;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Helper\ViewModel;
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

  if ($this->getUserTable()->authenticate($this->params()->fromQuery('password')))
   return new JsonModel(array('success' => true));
  else
   return new JsonModel(array(
    'success' => false,
    'message' => "Could not authenticate user",
   ));

 }

 public function GetQuestionsAction()
 {
  return new JsonModel(array('success' => true, 'questions' => $this->getUserTable()->getQuestions()));
 }

 public function SetQuestionAction()
 {
  $question = $this->params()->fromQuery('question');

  $validator_chain = new \Zend\Validator\ValidatorChain();
  $validator_chain->attach(new \Zend\Validator\Digits());
  $validator_chain->attach(new \Zend\Validator\Between(array('inclusive' => true, 'min' => 0, 'max' => 2)));

  if (!$validator_chain->isValid($question))
   return new JsonModel(array('success' => false, 'messages' => $validator_chain->getMessages()));

  $this->getUserTable()->setQuestion(strtolower($question));

  return new JsonModel(array('success' => true));
 }

 public function GetUserAction()
 {
  $user = $this->getUserTable()->getUser();

  return new JsonModel(array('success' => true, 'user' => $user->toArray()));
 }

 public function CheckAnswerAction()
 {
  $answer = $this->params()->fromQuery('answer');

  if (strtolower($this->getUserTable()->getUser()->getAnswer()) == strtolower($answer))
   return new JsonModel(array('success' => true));

  return new JsonModel(array('success' => false, 'message' => 'Could not verify answer'));
 }

 public function SetAnswerAction()
 {
  $answer = $this->params()->fromQuery('answer');

  $validator_chain = new \Zend\Validator\ValidatorChain();
  $validator_chain->attach(new \Zend\Validator\StringLength(array('min' => 1, 'max' => 30)));

  if(!$validator_chain->isValid($answer))
   return new JsonModel(array('success' => false, 'messages' => $validator_chain->getMessages()));

  $this->getUserTable()->setAnswer($answer);

  return new JsonModel(array('success' => true));
 }

 public function SetPasswordAction()
 {
  $user = $this->getUserTable()->getUser();
  $password = $this->params()->fromQuery('password');
  $validator = $user->getPasswordValidator();

  if (!$validator->isValid($password))
   return new JsonModel(array('success' => false, 'messages' => $validator->getMessages()));

  $this->getUserTable()->setPassword($password);

  return new JsonModel(array('success' => true));
 }

 /**
  * Return a Facebook object
  * @return \Facebook
  */
 public function getFacebook()
 {
  if ($this->facebook) return $this->facebook;
  $this->facebook = new \Facebook(array('appId' => '654412204576554', 'secret' => 'bebd056f6d6ff934cc48e36536b58318'));
  return $this->facebook;
 }

 public function getFbId()
 {
  if ($this->fb_id) return $this->fb_id;
  $this->fb_id = $this->getFacebook()->getUser();
  return $this->fb_id;
 }

 /**
  * @return \User\Service\UserTable
  */
 public function getUserTable()
 {
  if ($this->userTable) return $this->userTable;

  $this->userTable = $this->getServiceLocator()->get('User\Service\UserTable');
  $this->userTable->setFacebook($this->getFacebook());
  $this->userTable->setFbId($this->getFbId());

  return $this->userTable;
 }


}

