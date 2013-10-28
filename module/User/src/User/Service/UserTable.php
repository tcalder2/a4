<?php

namespace User\Service;

use Zend\Db\TableGateway\TableGateway;
use User\Entity\User;
use Zend\Db\ResultSet\ResultSet;
use Zend\Db\Sql\Select;

class UserTable
{
 /** @var \Doctrine\ORM\EntityManager $userTable */
 protected $em;

 /** @var  \Facebook $facebook */
 protected $facebook;

 protected $fb_id;

 public function __construct(\Zend\ServiceManager\ServiceManager $sm)
 {
  $this->em = $sm->get('Doctrine\ORM\EntityManager');
 }

 /**
  * @param $password
  */
 public function setPassword($password)
 {
  $user = $this->getUser();
  $user->setPassword(md5($password));
  $this->em->persist($user);
  $this->em->flush();
 }

 /**
  * @param $password
  * @return bool
  */
 public function authenticate($password)
 {
  $user = $this->getUser();
  $authenticated = $user->getPassword() == md5($password);

  if($authenticated)
   $user->setIncorrectPasswordAttempts(0);
  else
   $user->setIncorrectPasswordAttempts($user->getIncorrectPasswordAttempts() + 1);

  $this->em->persist($user);
  $this->em->flush();
  return $authenticated;
 }

 public function newUser($data)
 {
  $user = new User();

  $user->exchangeArray($data);
  $user->setPassword(md5('cs2212'));

  $this->em->persist($user);
  $this->em->flush();

  return $user;
 }

 /**
  * @return \User\Entity\User
  * @throws \Exception
  */
 public function getUser()
 {
  return $this->em->getRepository('User\Entity\User')->findOneBy(array('fb_id' => $this->fb_id));
 }

 /**
  * @param User $user
  */
 public function saveUser(User $user)
 {
  $this->em->persist($user);
 }

 /**
  * @return User[]
  */
 public function fetchAll()
 {
  return $this->em->getRepository('User\Entity\User')->findAll();
 }

 public function setFbId($fb_id)
 {
  $this->fb_id = $fb_id;
 }

 public function getFbId()
 {
  return $this->fb_id;
 }

 public function setFacebook(\Facebook $facebook)
 {
  $this->facebook = $facebook;
 }

 /**
  * Return a Facebook object
  * @return \Facebook
  */
 public function getFacebook()
 {
  return $this->facebook;
 }

}