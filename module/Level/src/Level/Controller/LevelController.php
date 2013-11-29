<?php

namespace Level\Controller;

use Level\Entity\Level;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\Validator\Digits;
use Zend\View\Model\JsonModel;

class LevelController extends AbstractActionController
{
 /** @var  \Level\Service\LevelTable */
 protected $level_table;


 public function getLevelsAction()
 {
  return new JsonModel(array('success' => true, 'levels' => $this->getLevelTable()->getLevels()));
 }

 public function getLevelAction()
 {
  $level_number = $this->params()->fromQuery('level');

  $level = $this->getLevelTable()->getLevel($level_number);

  if(!$level)
   return new JsonModel(array('success' => false, 'message' => 'Could not find that level'));

  return new JsonModel(array('success' => true, 'level' => $level->toArray()));
 }

 public function saveGameAction()
 {
  $mistakes = $this->params()->fromQuery('mistakes');
  $score = $this->params()->fromQuery('score');
  $time = $this->params()->fromQuery('time');
  $progeny_id = $this->params()->fromQuery('progeny_id');
  $level_number = $this->params()->fromQuery('level');

  $level = $this->getLevelTable()->getLevel($level_number);
  
  /** @var \Progeny\Service\ProgenyTable $progeny */
  $progeny = $this->getServiceLocator()->get('Progeny\Entity\ProgenyTable')->getProgeny($progeny_id);

  $mistakes_validator = new Digits();
  $score_validator = new Digits();
  $time_validator = new Digits();

  if(!$mistakes_validator->isValid($mistakes))
   return new JsonModel(array('success' => false, 'messages' => $mistakes_validator->getMessages()));

  if(!$score_validator->isValid($score))
   return new JsonModel(array('success' => false, 'messages' => $score_validator->getMessages()));

  if(!$time_validator->isValid($time))
   return new JsonModel(array('success' => false, 'messages' => $time_validator->getMessages()));

 }

 public function changeMistakesAllowedAction()
 {
  $level_number = $this->params()->fromQuery('level');
  $level = $this->getLevelTable()->getLevel($level_number);

  $mistakes_allowed = $this->params()->fromQuery('mistakes_allowed');
  $mistakes_allowed_validator = new Digits();

  if(!$mistakes_allowed_validator->isValid($mistakes_allowed))
   return new JsonModel(array('success' => false, 'messages' => $mistakes_allowed_validator->getMessages()));

  if(!$level)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a Level with that ID'));

  $this->getLevelTable()->changeMistakesAllowed($level, $mistakes_allowed);

  return new JsonModel(array('success' => true, 'level' => $level->toArray()));
 }

 /**
  * Return a Facebook object
  * @return \Facebook
  */
 public function getFacebook()
 {
  if ($this->facebook) return $this->facebook;
  $this->facebook = $this->getServiceLocator()->get('Application\Service\Facebook');
  return $this->facebook;
 }

 public function getFbId()
 {
  if ($this->fb_id) return $this->fb_id;
  $this->fb_id = $this->getServiceLocator()->get('Application\Service\FbId');
  return $this->fb_id;
 }

 /**
  * @return \Level\Service\LevelTable
  */
 public function getLevelTable()
 {
  if ($this->level_table) return $this->level_table;

  $this->level_table = $this->getServiceLocator()->get('Level\Service\LevelTable');

  return $this->level_table;
 }

}

