<?php

namespace Progeny\Service;

use Zend\Db\TableGateway\TableGateway;
use Progeny\Entity\Progeny;
use Zend\Db\ResultSet\ResultSet;
use Zend\Db\Sql\Select;

class ProgenyTable
{
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

 public function checkFirstNameUnique($first_name)
 {
  $qb = $this->em->createQueryBuilder();

  /** @var \User\Entity\User $user */
  $user = $this->sm->get('User\Service\User');

  return !$qb->select('p')
   ->from('\Progeny\Entity\Progeny', 'p')
   ->where(array('user' => $user, 'first_name' => $first_name));
 }

 public function newProgeny($data)
 {
  $progeny = new Progeny();

  $progeny->exchangeArray($data);

  $this->em->persist($progeny);
  $this->em->flush();

  return $progeny;
 }

 /**
  * @return \Progeny\Entity\Progeny
  * @throws \Exception
  */
 public function getProgeny()
 {
  return $this->em->getRepository('Progeny\Entity\Progeny')->findOneBy(array('fb_id' => $this->fb_id));
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


 public function getProgeniesArray()
 {
  /** @var \User\Entity\User $user */
  $user = $this->sm->get( 'User\Entity\User');
  $progenies = $this->em->getRepository('User\Entity\User')->findBy(array('user' => $user));

  $results = Array();

  foreach ($progenies as $progeny)
  {
   /** @var \Progeny\Entity\Progeny $progeny */
   array_push($results, $progeny->toArray());
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

