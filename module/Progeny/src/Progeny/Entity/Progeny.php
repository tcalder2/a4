<?php

namespace Progeny\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

/**
 * @ORM\Entity
 * @ORM\Table(name="child")
 */
class Progeny
{
 protected static $birth_date_validator;
 protected static $first_name_validator;
 protected static $time_allowed_validator;

 /**
  * @return \Zend\Validator\Date
  */
 public static function getBirthDateValidator()
 {
  return (Progeny::$birth_date_validator = new \Zend\Validator\Date());
 }

 public static function getFirstNameValidator()
 {
  return (Progeny::$first_name_validator = new \Zend\Validator\StringLength(array('min' => 1, 'max' => 20)));
 }

 /**
  * @return \Zend\Validator\Digits()
  */
 public static function getTimeAllowedValidator()
 {
  return (Progeny::$time_allowed_validator = new \Zend\Validator\Digits());
 }

 function __construct()
 {
  $this->is_deleted = false;
 }

 public function toArray()
 {
  return array(
   'first_name' => $this->getFirstName(),
   'birth_date' => $this->getBirthDate()->format("Y-m-d"),
   'id' => $this->getId(),
   'time_allowed' => $this->getTimeAllowed(),
   'level' => $this->getLevel(),
   'final_game_high_score' => $this->getFinalGameHighScore(),
  );
 }

 public function setInputFilter(InputFilterInterface $i)
 {
  throw new \Exception("Not used");
 }

 public function exchangeArray($data)
 {
  $this->first_name = (isset($data['first_name'])) ? $data['first_name'] : null;
  $this->birth_date = (isset($data['birth_date'])) ? $data['birth_date'] : null;
  $this->time_allowed = (isset($data['time_allowed'])) ? $data['time_allowed'] : null;
  $this->level = (isset($data['level'])) ? $data['level'] : null;
 }

 /**
  * @param mixed $first_name
  */
 public function setFirstName($first_name)
 {
  $this->first_name = $first_name;
 }

 /**
  * @return mixed
  */
 public function getFirstName()
 {
  return $this->first_name;
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
  * @param mixed $is_deleted
  */
 public function setIsDeleted($is_deleted)
 {
  $this->is_deleted = $is_deleted;
 }

 /**
  * @return mixed
  */
 public function getIsDeleted()
 {
  return $this->is_deleted;
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

 /**
  * @param mixed $birth_date
  */
 public function setBirthDate($birth_date)
 {
  $this->birth_date = $birth_date;
 }

 /**
  * @return \DateTime
  */
 public function getBirthDate()
 {
  return $this->birth_date;
 }

 /**
  * @param mixed $time_allowed
  */
 public function setTimeAllowed($time_allowed)
 {
  $this->time_allowed = $time_allowed;
 }

 /**
  * @return mixed
  */
 public function getTimeAllowed()
 {
  return $this->time_allowed;
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
  * @param mixed $final_game_high_score
  */
 public function setFinalGameHighScore($final_game_high_score)
 {
  $this->final_game_high_score = $final_game_high_score;
 }

 /**
  * @return mixed
  */
 public function getFinalGameHighScore()
 {
  return $this->final_game_high_score;
 }

 /**
  * @ORM\Id
  * @ORM\GeneratedValue(strategy="AUTO")
  * @ORM\Column(type="integer")
  */
 protected $id;

 /** @ORM\ManyToOne(targetEntity="User\Entity\User") */
 protected $user;

 //Each child should have a different first name (1 to 20 characters) and an age (or birthday).
 //A Facebook user must be able to change the age on his/her child (but not the first name, if the first name is to change, then the user must delete the child and recreate it)
 /** @ORM\Column(type="string") */
 protected $first_name;

 /** @ORM\Column(type="date") */
 protected $birth_date;

 //A Facebook user must be able to delete his/her children
 /** @ORM\Column(type="boolean") */
 protected $is_deleted;

 /** @ORM\Column(type="integer") */
 protected $time_allowed;

 /** @ORM\Column(type="integer") */
 protected $level;

 /** @ORM\Column(type="integer") */
 protected $final_game_high_score;


}

