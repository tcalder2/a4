<?php

namespace Level\Service;

use Level\Entity\Level;
use Zend\ServiceManager\ServiceManager;

class LevelTable
{
 /** @var \Doctrine\ORM\EntityManager $levelTable */
 protected $em;

 /** @var  \Facebook $facebook */
 protected $facebook;

 protected $fb_id;

 /** @var ServiceManager $sm */
 protected $sm;

 public function __construct(ServiceManager $sm)
 {
  $this->sm = $sm;
  $this->em = $this->sm->get('Doctrine\ORM\EntityManager');
 }

 public function removeLevel($level)
 {
  $this->em->remove($level);
  $this->em->flush();
 }

 public function newLevel($data)
 {
  $level = new Level();

  $level->exchangeArray($data);
  $level->setUser($this->sm->get('User\Service\User'));

  $this->em->persist($level);
  $this->em->flush();

  return $level;
 }

 /**
  * @param $level_id
  * @return \Level\Entity\Level
  */
 public function getLevel($level_id)
 {
  return $this->em->getRepository('Level\Entity\Level')->findOneBy(
   array(
    'user' => $this->sm->get('User\Service\User'),
    'id' => $level_id
   ));
 }

 /**
  * @param Level $level
  */
 public function saveLevel(Level $level)
 {
  $this->em->persist($level);
 }

 public function changeMistakesAllowed(Level $level, $mistakes_allowed)
 {
  $level->setMistakesAllowed($mistakes_allowed);
  $this->em->persist($level);
  $this->em->flush();
 }

 /**
  * @return Level[]
  */
 public function fetchAll()
 {
  return $this->em->getRepository('Level\Entity\Level')->findAll();
 }

 public function setMistakesAllowed(Level $level, $mistakes_allowed)
 {
  $level->setMistakesAllowed($mistakes_allowed);
  $this->em->persist($level);
  $this->em->flush();
 }

 public function getLevelsArray()
 {
  /** @var \User\Entity\User $user */
  $user = $this->sm->get('User\Service\User');

  $levels = $this->em->getRepository('Level\Entity\Level')->findBy(array('user' => $user));

  $results = Array();

  foreach ($levels as $level)
  {
   /** @var \Level\Entity\Level $level */
   array_push($results, $level->toArray());
  }

  return $results;
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

