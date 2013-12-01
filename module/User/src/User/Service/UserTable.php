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

 protected $facebook;

 protected $fb_id;

 /** @var \Zend\ServiceManager\ServiceManager $sms */
 protected $sm;

 //Preset questions
 protected $questions = array(
  0 => 'What\'s your mother\'s maiden name?',
  1 => 'What\'s the name of your favourite pet?',
  2 => 'Other than CS2212, what is your favourite class/subject?',
 );

 public function __construct(\Zend\ServiceManager\ServiceManager $sm)
 {
  $this->sm = $sm;
  $this->em = $this->sm->get('Doctrine\ORM\EntityManager');
 }

 public function setSkin($skin)
 {
  $user = $this->getUser();
  $user->setSkin($skin);

  $this->em->persist($user);
  $this->em->flush();
 }

 public function setAnswer($answer)
 {
  //persist answer
  $user = $this->getUser();
  $user->setAnswer($answer);
  $this->em->persist($user);
  $this->em->flush();
 }

 public function setQuestion($question)
 {
  //persist qustion array index
  $user = $this->getUser();
  $user->setQuestion($question);
  $this->em->persist($user);
  $this->em->flush();
 }

 public function getQuestions()
 {
  //return the questoins array
  return $this->questions;
 }

 /**
  * @param $password
  */
 public function setPassword($password)
 {
  //encrypt and persist the password
  $user = $this->getUser();
  $user->setPassword(md5($password));
  $user->setIncorrectPasswordAttempts(0);
  $this->em->persist($user);
  $this->em->flush();
 }

 /**
  * @param $password
  * @return bool
  */
 public function authenticate($password)
 {
  //encrypt the incomming password
  $user = $this->getUser();
  
  //challenge
  $authenticated = $user->getPassword() == md5($password);

  if($authenticated)
   //reset password attemts to 0
   $user->setIncorrectPasswordAttempts(0);
  else
   //slap the user's hand
   $user->setIncorrectPasswordAttempts($user->getIncorrectPasswordAttempts() + 1);

  //persist the user to save the password attempt count
  $this->em->persist($user);
  $this->em->flush();
  return $authenticated;
 }

 public function getFriends()
 {

   $fb_friends = $this->getFacebook()->api(array(
    'method' => 'fql.query',
    'query' => 'SELECT first_name, last_name, uid, name, is_app_user FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1=me()) AND is_app_user=1'
   ));


  $friends_array = array();
  $friends = array();

  foreach($fb_friends as $fb_friend)
  {
   array_push($friends, array(
    'first_name' => $fb_friend['first_name'],
    'last_name' => $fb_friend['last_name'],
    'fb_id' => $fb_friend['uid'],
   ));
  }

  $user = $this->getUser();

  array_push($friends, array(
   'first_name' => $user->getFirstName(),
   'last_name' => $user->getLastName(),
   'fb_id' => $user->getFbId(),
  ));

  $user_repository = $this->em->getRepository('User\Entity\User');
  $progeny_repository = $this->em->getRepository('Progeny\Entity\Progeny');

  /** @var \Progeny\Service\ProgenyTable $progeny_table */
  $progeny_table = $this->sm->get('Progeny\Service\ProgenyTable');

  foreach($friends as $friend)
  {
   $friend_array = array();

   $friend_array['first_name'] = $friend['first_name'];
   $friend_array['last_name'] = $friend['last_name'];
   $friend_array['fb_id'] = $friend['fb_id'];

   if(isset($progenies_array))
    unset($progenies_array);

   $progenies_array = array();

   /** @var \User\Entity\User $friend */
   $friend_obj = $user_repository->findBy(array('fb_id' => $friend['fb_id']));

   if(!$friend_obj) continue;

   $progenies = $progeny_repository->findBy(array('user' => $friend_obj));

   foreach($progenies as $progeny)
   {
    /** @var \Progeny\Entity\Progeny $progeny */

    array_push($progenies_array, $progeny_table->getProgenyDataArray($progeny));
   }

   $friend_array['progenies'] = $progenies_array;

   array_push($friends_array, $friend_array);
  }

  return $friends_array;
 }

 public function getAllFbUsers()
 {

  $users = $this->em->getRepository('\User\Entity\User')->findAll();


  $friends_array = array();
  $friends = array();

  foreach($users as $fb_user)
  {
   /** @var \User\Entity\User $fb_user */
   array_push($friends, array(
    'first_name' => $fb_user->getFirstName(),
    'last_name' => $fb_user->getLastName(),
    'fb_id' => $fb_user->getFbId(),
   ));
  }

  $user_repository = $this->em->getRepository('User\Entity\User');
  $progeny_repository = $this->em->getRepository('Progeny\Entity\Progeny');

  /** @var \Progeny\Service\ProgenyTable $progeny_table */
  $progeny_table = $this->sm->get('Progeny\Service\ProgenyTable');

  foreach($friends as $friend)
  {
   $friend_array = array();

   $friend_array['first_name'] = $friend['first_name'];
   $friend_array['last_name'] = $friend['last_name'];
   $friend_array['fb_id'] = $friend['fb_id'];

   if(isset($progenies_array))
    unset($progenies_array);

   $progenies_array = array();

   /** @var \User\Entity\User $friend */
   $friend_obj = $user_repository->findBy(array('fb_id' => $friend['fb_id']));

   if(!$friend_obj) continue;

   $progenies = $progeny_repository->findBy(array('user' => $friend_obj));

   foreach($progenies as $progeny)
   {
    /** @var \Progeny\Entity\Progeny $progeny */

    array_push($progenies_array, $progeny_table->getProgenyDataArray($progeny));
   }

   $friend_array['progenies'] = $progenies_array;

   array_push($friends_array, $friend_array);
  }

  return $friends_array;
 }

 public function newUser($data)
 {
  //create a new user from the incomming data
  $user = new User();

  $user->exchangeArray($data);
  $user->setPassword(md5('cs2212'));
  $user->setSkin(0);
  $user->setQuestion(0);
  $user->setIncorrectPasswordAttempts(0);

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
  //get the singleton user
  return $this->em->getRepository('User\Entity\User')->findOneBy(array('fb_id' => $this->getFbId()));
 }

 /**
  * @param User $user
  */
 public function saveUser(User $user)
 {
  //persist the user in all its glory
  $this->em->persist($user);
 }

 /**
  * @return User[]
  */
 public function fetchAll()
 {
  //return all user objects
  //this will be important in the future
  return $this->em->getRepository('User\Entity\User')->findAll();
 }

 public function getFbId()
 {
  if ($this->fb_id) return $this->fb_id;
  $this->fb_id = $this->sm->get('Application\Service\FbId');
  return $this->fb_id;
 }

 /**
  * Return a Facebook object
  * @return \Facebook
  */
 public function getFacebook()
 {
  if($this->facebook) return $this->facebook;
  $this->facebook = $this->sm->get('Application\Service\Facebook');
  return $this->facebook;
 }
}