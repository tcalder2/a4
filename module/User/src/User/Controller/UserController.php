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

