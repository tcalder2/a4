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


 public function loginFacebookAction()
 {
  $facebook = $this->getFacebook();

  $fb_uid = $facebook->getUser();

  if($fb_uid) $this->redirect()->toRoute('home');

  return new ViewModel(array('facebook' => $facebook));

 }

 public function indexAction()
 {
  $facebook = $this->getFacebook();
  $fb_uid = $facebook->getUser();

  if(!$fb_uid) return $this->redirect()->toRoute('loginfacebook');

  try {
   $user_profile = $facebook->api('/'.$fb_uid, 'GET');
  }
  catch(\Exception $e)
   {
   $facebook->destroySession();
   return $this->redirect()->toRoute('loginfacebook');
  }

  $em = $this->getObjectManager();

  /** @var \Application\Entity\User $user */
  $user = $em->getRepository('Application\Entity\User')->findOneBy(array('fb_id' => $user_profile['id']));

  if(!$user && $fb_uid)
  {
   $user = new \Application\Entity\User();
   $user->exchangeArray($user_profile);

   $em->persist($user);
   $em->flush();
  }


  return new ViewModel(array('facebook' => $facebook, 'user_profile' => $user_profile));
 }


 /**
  * Doctrine Object Manager
  * @return \Doctrine\ORM\EntityManager
  */
 public function getObjectManager()
 {
  if ($this->objectManager) return $this->objectManager;

  $this->objectManager = $this
   ->getServiceLocator()
   ->get('Doctrine\ORM\EntityManager');

  return $this->objectManager;
 }

 public function getFacebook()
 {
  if($this->facebook) return $this->facebook;

  $this->facebook = new \Facebook(array('appId' => '654412204576554', 'secret' => 'bebd056f6d6ff934cc48e36536b58318'));

  return $this->facebook;
 }
}
