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

 protected $inputFilter;
 protected static $birth_date_validator;
 protected static $first_name_validator;

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

 function __construct()
 {
 }

 public function toArray()
 {
  return array(
   'first_name' => $this->getFirstName(),
   'birth_date' => $this->birth_date(),
   'id' => $this->getId(),
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


 /** @ORM\ManyToOne(targetEntity="User\Entity\User") */
 protected $user;

// /** @ORM\Column(type="string", nullable=true) */
// protected $fb_id;

 //Each child should have a different first name (1 to 20 characters) and an age (or birthday).
 //A Facebook user must be able to change the age on his/her child (but not the first name, if the first name is to change, then the user must delete the child and recreate it)
 /** @ORM\Column(type="string") */
 protected $first_name;

 /** @ORM\Column(type="date") */
 protected $birth_date;

 //A Facebook user must be able to delete his/her children
 /** @ORM\Column(type="boolean") */
 protected $is_deleted;

 //A Facebook user must be able to set the time for each level per child (thus one child may be given 1 minute to complete a level but another child may be given 2 minutes to complete a level). Make the default time 30 seconds for each child.
 /** @ORM\Column(type="string") */
 protected $minutes_to_complete_level;

 //Initially each child starts at the beginner level
 //A Facebook user must be able to see which level his/her children have achieved
 /** @ORM\Column(type="string") */
 protected $level;

 //A Facebook user must be able to set the number of mistakes the children can make per level (maybe they can not make any mistake for the ones table, but for the 9 tables, they can make up to 3 mistakes and still go on to the next level). The mistake is per level, but the time is per child
 /** @ORM\Column(type="string") */
 protected $mistakes_allowed_per_level;


// /** @ORM\Column(type="string", nullable=true) */
// protected $fb_link;

 public function exchangeArray($data)
 {
  $this->first_name = (isset($data['first_name'])) ? $data['first_name'] : null;
  $this->birth_date = (isset($data['birth_date'])) ? $data['birth_date'] : null;
 }

 /**
  * @param mixed $age
  */
 public function setAge($age)
 {
  $this->age = $age;
 }

 /**
  * @return mixed
  */
 public function getAge()
 {
  return $this->age;
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
  * @param mixed $last_name
  */
 public function setLastName($last_name)
 {
  $this->last_name = $last_name;
 }

 /**
  * @return mixed
  */
 public function getLastName()
 {
  return $this->last_name;
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
  * @param mixed $minutes_to_complete_level
  */
 public function setMinutesToCompleteLevel($minutes_to_complete_level)
 {
  $this->minutes_to_complete_level = $minutes_to_complete_level;
 }

 /**
  * @return mixed
  */
 public function getMinutesToCompleteLevel()
 {
  return $this->minutes_to_complete_level;
 }

 /**
  * @param mixed $mistakes_allowed_per_level
  */
 public function setMistakesAllowedPerLevel($mistakes_allowed_per_level)
 {
  $this->mistakes_allowed_per_level = $mistakes_allowed_per_level;
 }

 /**
  * @return mixed
  */
 public function getMistakesAllowedPerLevel()
 {
  return $this->mistakes_allowed_per_level;
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

