<?php

namespace Level\Entity;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

/**
 * @ORM\Entity
 * @ORM\Table(name="level")
 */
class Level {

 protected static $mistakes_allowed_validator;

 /**
  * @return \Zend\Validator\Date
  */
 public static function getMistakesAllowedValidator()
 {
  return (Progeny::$mistakes_allowed_validator = new \Zend\Validator\Digits());
 }


 function __construct()
 {
  $this->mistakes_allowed = 0;
 }

 public function toArray()
 {
  return array(
   'mistakes_allowed' => $this->getMistakesAllowed(),
   'id' => $this->getId(),
   'level' => $this->getLevel(),
  );
 }

 public function setInputFilter(InputFilterInterface $i)
 {
  throw new \Exception("Not used");
 }

 /**
  * @ORM\Id
  * @ORM\GeneratedValue(strategy="AUTO")
  * @ORM\Column(type="integer")
  */
 protected $id;

 /** @ORM\Column(type="integer") */
 protected $level;

 /** @ORM\ManyToOne(targetEntity="User\Entity\User") */
 protected $user;

 /** @ORM\Column(type="string") */
 protected $mistakes_allowed;

 public function exchangeArray($data)
 {
  $this->mistakes_allowed = (isset($data['mistakes_allowed'])) ? $data['mistakes_allowed'] : null;
 }

 /**
  * @param mixed $id
  */
 public function setId($id)
 {
  $this->id = $id;
 }

 /**
  * @return mixed
  */
 public function getId()
 {
  return $this->id;
 }

 /**
  * @param mixed $level
  */
 public function setLevel($level)
 {
  $this->level = $level;
 }

 /**
  * @return mixed
  */
 public function getLevel()
 {
  return $this->level;
 }

 /**
  * @param mixed $mistakes_allowed
  */
 public function setMistakesAllowed($mistakes_allowed)
 {
  $this->mistakes_allowed = $mistakes_allowed;
 }

 /**
  * @return mixed
  */
 public function getMistakesAllowed()
 {
  return $this->mistakes_allowed;
 }

 /**
  * @param mixed $user
  */
 public function setUser($user)
 {
  $this->user = $user;
 }

 /**
  * @return mixed
  */
 public function getUser()
 {
  return $this->user;
 }


}

