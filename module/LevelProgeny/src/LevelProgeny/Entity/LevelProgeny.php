<?php

namespace LevelProgeny\Entity;
use Doctrine\ORM\Mapping as ORM;
use Zend\InputFilter\InputFilterInterface;

/**
 * @ORM\Entity
 * @ORM\Table(name="level_progeny")
 */
class LevelProgeny {

 function __construct()
 {
  $this->final_completion_time = 0;
  $this->high_score = 0;
  $this->final_mistakes = 0;
  $this->attempts = 0;
 }

 public function toArray()
 {
  return array(
   'id' => $this->getId(),
   'attempts' => $this->getAttempts(),
   'final_mistakes' => $this->getFinalMistakes(),
   'final_completion_time' => $this->final_completion_time,
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

 /** @ORM\ManyToOne (targetEntity="Progeny\Entity\Progeny") */
 protected $progeny;

 /** @ORM\ManyToOne(targetEntity="User\Entity\User") */
 protected $user;

 /** @ORM\Column(type="integer") */
 protected $attempts;

 /** @ORM\Column(type="integer") */
 protected $final_completion_time;

 /** @ORM\Column(type="integer") */
 protected $final_mistakes;

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

 /**
  * @param mixed $final_mistakes
  */
 public function setFinalMistakes($final_mistakes)
 {
  $this->final_mistakes = $final_mistakes;
 }

 /**
  * @return mixed
  */
 public function getFinalMistakes()
 {
  return $this->final_mistakes;
 }

 public function exchangeArray($data)
 {
  $this->final_completion_time = (isset($data['final_completion_time'])) ? $data['final_completion_time'] : null;
  $this->high_score = (isset($data['high_score'])) ? $data['high_score'] : null;
  $this->final_mistakes = (isset($data['final_mistakes'])) ? $data['final_mistakes'] : null;
  $this->attempts = (isset($data['attempts'])) ? $data['attempts'] : null;
 }


}

