<?php

namespace Progeny\Service;

use LevelProgeny\Entity\LevelProgeny;
use Progeny\Entity\Progeny;

class ProgenyTable
{
 private $level_progeny_table;
 /** @var \Doctrine\ORM\EntityManager $progenyTable */
 protected $em;

 /** @var  \Facebook $facebook */
 protected $facebook;

 protected $fb_id;

 /** @var \Zend\ServiceManager\ServiceManager $sm */
 protected $sm;

 public function __construct(\Zend\ServiceManager\ServiceManager $sm)
 {
  $this->sm = $sm;
  $this->em = $this->sm->get('Doctrine\ORM\EntityManager');
 }

 public function removeProgeny(Progeny $progeny)
 {
  $level_progenys = $this->em->getRepository('LevelProgeny\Entity\LevelProgeny')->findBy(array('progeny' => $progeny));
  foreach($level_progenys as $level_progeny)
  {
   $this->em->remove($level_progeny);
  }

  $this->em->remove($progeny);
  $this->em->flush();
 }

 public function checkFirstNameUnique($first_name)
 {
  $qb = $this->em->createQueryBuilder();

  /** @var \User\Entity\User $user */
  $user = $this->sm->get('User\Service\User');

//  $result = $qb->select('p')->from('Progeny\Entity\Progeny', 'p')
//    ->where('p.user = :user AND p.first_name = :first_name')
//    ->setParameter('user', $user)
//    ->setParameter('first_name', $first_name)
//    ->getQuery()
//    ->getResult();

  $result = $this->em->getRepository('Progeny\Entity\Progeny')->findOneBy(array('user' => $user, 'first_name' => $first_name));

  return !$result;
 }

 public function saveFinalGame(Progeny $progeny, $score)
 {
  if ($score <= $progeny->getFinalGameHighScore()) return;

  $progeny->setFinalGameHighScore($score);
  $this->em->persist($progeny);
  $this->em->flush();
 }

 public function newProgeny($data)
 {
  $level_progeny_table = $this->getLevelProgenyTable();


  $progeny = new Progeny();

  $progeny->exchangeArray($data);
  $progeny->setFinalGameHighScore(0);
  $progeny->setUser($this->sm->get('User\Service\User'));

  $this->em->persist($progeny);
  $this->em->flush();

  $level_progeny_table->newLevelProgenys($progeny);

  return $progeny;
 }

 /**
  * @param $progeny_id
  * @return \Progeny\Entity\Progeny
  */
 public function getProgeny($progeny_id)
 {
  return $this->em->getRepository('Progeny\Entity\Progeny')->findOneBy(
   array(
    'user' => $this->sm->get('User\Service\User'),
    'id' => $progeny_id
   ));
 }

 public function setLevel(Progeny $progeny, $level)
 {
  $progeny->setLevel($level);

  $this->em->persist($progeny);
  $this->em->flush();
 }

 public function getProgenyDataArray(Progeny $progeny)
 {
  /** @var \LevelProgeny\Service\LevelProgenyTable $level_progeny_table */
  $level_progeny_table = $this->sm->get('LevelProgeny\Service\LevelProgenyTable');

  $level_progenys = $level_progeny_table->getLevelProgenys($progeny);
  $progeny_array = $progeny->toArray();
  $progeny_array['level_progenys'] = $level_progenys;

  return $progeny_array;
 }

 public function updateBirthDate($progeny, $birth_date)
 {
  $progeny->setBirthDate($birth_date);
  $this->em->persist($progeny);
  $this->em->flush();
 }

 /**
  * @param Progeny $progeny
  */
 public function saveProgeny(Progeny $progeny)
 {
  $this->em->persist($progeny);
 }

 /**
  * @return Progeny[]
  */
 public function fetchAll()
 {
  return $this->em->getRepository('Progeny\Entity\Progeny')->findAll();
 }

 public function setTimeAllowed(Progeny $progeny, $time_allowed)
 {
  $progeny->setTimeAllowed($time_allowed);
  $this->em->persist($progeny);
  $this->em->flush();
 }

 public function getProgeniesArray()
 {
  /** @var \User\Entity\User $user */
  $user = $this->sm->get('User\Service\User');

  $progenies = $this->em->getRepository('Progeny\Entity\Progeny')->findBy(array('user' => $user));

  $results = Array();

  foreach ($progenies as $progeny)
  {
   /** @var \Progeny\Entity\Progeny $progeny */
   array_push($results, $this->getProgenyDataArray($progeny));
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

 /**
  * @return \LevelProgeny\Service\LevelProgenyTable
  */
 public function getLevelProgenyTable()
 {
  if ($this->level_progeny_table) return $this->level_progeny_table;

  /** @var \LevelProgeny\Service\LevelProgenyTable $level_progeny_table */
  $this->level_progeny_table = $this->sm->get('LevelProgeny\Service\LevelProgenyTable');

  return $this->level_progeny_table;
 }

}

