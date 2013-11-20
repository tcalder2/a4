<?php

namespace LevelProgeny\Entity;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

/**
 * @ORM\Entity
 * @ORM\Table(name="level_progeny")
 */
class LevelProgeny {

// protected static $mistakes_allowed_validator;
//
// /**
//  * @return \Zend\Validator\Date
//  */
// public static function getMistakesAllowedValidator()
// {
//  return (LevelProgeny::$mistakes_allowed_validator = new \Zend\Validator\Digits());
// }


 function __construct()
 {
 }

 public function toArray()
 {
  return array(
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

 /** @ORM\Column(type="integer") */
 protected $level;

 /** @ORM\OneToOne targetEntity="Progeny\Entity\Progeny") */
 protected $progeny;

 /** @ORM\ManyToOne(targetEntity="User\Entity\User") */
 protected $user;

 /** @ORM\Column(type="integer") */
 protected $attempts;

 /** @ORM\Column(type="integer") */
 protected $final_completion_time;

 /** @ORM\Column(type="integer") */
 protected $final_game_high_score;

 /** @ORM\Column(type="integer") */
 protected $mistakes;

 public function exchangeArray($data)
 {
  //$this->mistakes_allowed = (isset($data['mistakes_allowed'])) ? $data['mistakes_allowed'] : null;
 }

 /**
  * @param mixed $attempts
  */
 public function setAttempts($attempts)
 {
  $this->attempts = $attempts;
 }

 /**
  * @return mixed
  */
 public function getAttempts()
 {
  return $this->attempts;
 }

 /**
  * @param mixed $final_completion_time
  */
 public function setFinalCompletionTime($final_completion_time)
 {
  $this->final_completion_time = $final_completion_time;
 }

 /**
  * @return mixed
  */
 public function getFinalCompletionTime()
 {
  return $this->final_completion_time;
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
  * @param mixed $mistakes
  */
 public function setMistakes($mistakes)
 {
  $this->mistakes = $mistakes;
 }

 /**
  * @return mixed
  */
 public function getMistakes()
 {
  return $this->mistakes;
 }

 /**
  * @param mixed $progeny
  */
 public function setProgeny($progeny)
 {
  $this->progeny = $progeny;
 }

 /**
  * @return mixed
  */
 public function getProgeny()
 {
  return $this->progeny;
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

