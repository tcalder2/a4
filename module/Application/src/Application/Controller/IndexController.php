<?php
/**
 * Zend Framework (http://framework.zend.com/)
 *
 * @link      http://github.com/zendframework/ZendSkeletonApplication for the canonical source repository
 * @copyright Copyright (c) 2005-2013 Zend Technologies USA Inc. (http://www.zend.com)
 * @license   http://framework.zend.com/license/new-bsd New BSD License
 */

namespace Application\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;

class IndexController extends AbstractActionController
{
 protected $objectManager;

 /** @var  \Facebook $facebook */
 protected $facebook;

 protected $fb_id;

 /** @var \User\Service\UserTable */
 protected $userTable;

 public function loginFacebookAction()
 {
  //If we're logged in, redirect to the home page
  if ($this->getFbId()) $this->redirect()->toRoute('home');

  //Render the page /module/Application/view/login-facebook.phtml with the variables needed in the array
  return new ViewModel(array('facebook' => $this->getFacebook()));
 }

 public function redirectHomeAction()
 {
  $this->redirect()->toRoute('home');
 }

 public function testAction()
 {
  if(!$this->getFbId())
   return $this->redirect()->toRoute('loginfacebook');

  //Render the page /module/Application/view/application/index.phtml
  //That page requires the variables in the array to run
  return new ViewModel();
 }

 public function index($view)
 {
  //Create a new Facebook object
  $fb_uid = $this->getFbId();

  //If we're not logged in, redirect to the login page
  if (!$fb_uid) return $this->redirect()->toRoute('loginfacebook');

  //Get the user profile or die
  try {
   $user_profile = $this->getServiceLocator()->get('Application\Service\FacebookProfile');
  } catch (\Exception $e) {
   //Clear the Facebook identity
   //This fixes a condition where an error occurred if the user disconnected the app
   $this->getFacebook()->destroySession();

   //Redirect to login again
   return $this->redirect()->toRoute('loginfacebook');
  }

  $user_table = $this->getUserTable();

  $user = $user_table->getUser($fb_uid);

  /** @var \Level\Service\LevelTable $level_table */
  $level_table = $this->getServiceLocator()->get('Level\Service\LevelTable');

  //If the user has not previously been persisted
  if (!$user && $fb_uid) {
   $user_table->newUser($user_profile);
   $level_table->addLevels();
  }

  $vm = new ViewModel(array('user_profile' => $user_profile, 'fb_id' => $fb_uid));
  $vm->setTemplate('application/index/'.$view.'.phtml');

  //Render the page /module/Application/view/application/index.phtml
  //That page requires the variables in the array to run
  return $vm;

 }

 public function yaqzanAction() { return $this->index("yaqzan"); }
 public function jamesaAction() { return $this->index("jamesa"); }
 public function jamesbAction() { return $this->index("jamesb"); }
 public function frankAction() { return $this->index("frank"); }
 public function taylorAction() { return $this->index("taylor"); }
 public function indexAction() { return $this->index("index"); }



 /**
  * Doctrine Object Manager
  * Returns an abstract entity translator between objects in the project and objects in the database
  * @return \Doctrine\ORM\EntityManager
  */
 public function getObjectManager()
 {
  if ($this->objectManager) return $this->objectManager;

  //Load the service
  $this->objectManager = $this
   ->getServiceLocator()
   ->get('Doctrine\ORM\EntityManager');

  return $this->objectManager;
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
}
