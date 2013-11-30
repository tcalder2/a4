<?php

namespace LevelProgeny\Service;

use Level\Entity\Level;
use LevelProgeny\Entity\LevelProgeny;
use Progeny\Entity\Progeny;
use Zend\ServiceManager\ServiceManager;

class LevelProgenyTable
{
 /** @var \Doctrine\ORM\EntityManager $levelProgenyTable */
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

 public function saveGame($time, $mistakes, Progeny $progeny, $level)
 {
  /** @var \LevelProgeny\Service\LevelProgenyTable $level_progeny_table */
  $level_progeny_table = $this->sm->get('LevelProgeny\Service\LevelProgenyTable');

  $level_progeny_table->getLevelProgeny($level, $progeny);

  $level_progeny = $this->getLevelProgeny($level, $progeny);

  $level_progeny->setFinalCompletionTime($time);
  $level_progeny->setFinalMistakes($mistakes);

  $level_progeny->setAttempts($level_progeny->getAttempts() + 1);

  $this->em->persist($level_progeny);
  $this->em->flush();
 }

 public function removeLevelProgeny($levelProgeny)
 {
  $this->em->remove($levelProgeny);
  $this->em->flush();
 }

 public function newLevelProgenys(Progeny $progeny)
 {
  $user = $this->sm->get('User\Service\User');

  for($x = 1; $x < 13; ++$x)
  {
   $levelProgeny = new LevelProgeny();

   $levelProgeny->setUser($user);
   $levelProgeny->setProgeny($progeny);
   $levelProgeny->setLevel($x);
   $levelProgeny->setAttempts(0);

   $this->em->persist($levelProgeny);
  }

  $this->em->flush();
 }

 /**
  * @param $level
  * @param $progeny
  * @internal param $levelProgeny
  * @return LevelProgeny
  */
 public function getLevelProgeny($level, $progeny)
 {
  return $this->em->getRepository('LevelProgeny\Entity\LevelProgeny')->findOneBy(
   array(
    'user' => $this->sm->get('User\Service\User'),
    'level' => $level,
    'progeny' => $progeny
   ));
 }

 public function getLevelProgenys(Progeny $progeny)
 {
  $levelProgenies = $this->em->getRepository('LevelProgeny\Entity\LevelProgeny')->
    findBy(array('progeny' => $progeny), array('level' => 'ASC'));

  $levelProgenies_array = array();

  foreach($levelProgenies as $levelProgeny)
  {
   /** @var LevelProgeny $levelProgeny */
   array_push($levelProgenies_array, $levelProgeny->toArray());
  }

  return $levelProgenies_array;
 }

 /**
  * @param LevelProgeny $levelProgeny
  */
 public function saveLevelProgeny(LevelProgeny $levelProgeny)
 {
  $this->em->persist($levelProgeny);
 }

 public function changeMistakesAllowed(LevelProgeny $levelProgeny, $mistakes_allowed)
 {
  $levelProgeny->setMistakesAllowed($mistakes_allowed);
  $this->em->persist($levelProgeny);
  $this->em->flush();
 }

 /**
  * @return LevelProgeny[]
  */
 public function fetchAll()
 {
  return $this->em->getRepository('LevelProgeny\Entity\LevelProgeny')->findAll();
 }

 public function setMistakesAllowed(LevelProgeny $levelProgeny, $mistakes_allowed)
 {
  $levelProgeny->setMistakesAllowed($mistakes_allowed);
  $this->em->persist($levelProgeny);
  $this->em->flush();
 }

 public function getLevelProgeniesArray()
 {
  /** @var \User\Entity\User $user */
  $user = $this->sm->get('User\Service\User');

  $levelProgenies = $this->em->getRepository('LevelProgeny\Entity\LevelProgeny')->findBy(array('user' => $user));

  $results = Array();

  foreach ($levelProgenies as $levelProgeny)
  {
   /** @var LevelProgeny $levelProgeny */
   array_push($results, $levelProgeny->toArray());
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

