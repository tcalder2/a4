<?php

namespace Level\Controller;

use Level\Entity\Level;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\JsonModel;
use Zend\View\Model\ViewModel;

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

 public function changeMistakesAllowedAction()
 {
  $level_id = $this->params()->fromQuery('level_id');
  $level = $this->getLevelTable()->getLevel($level_id);

  $mistakes_allowed = $this->params()->fromQuery('mistakes_allowed');
  $mistakes_allowed_validator = Level::getMistakesAllowedValidator();

  if(!$mistakes_allowed_validator->isValid($mistakes_allowed))
   return new JsonModel(array('success' => false, 'messages' => $mistakes_allowed_validator->getMessages()));

  if(!$level)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a Level with that ID'));

  $this->getLevelTable()->changeMistakesAllowed($level, $mistakes_allowed);

  return new ViewModel(array('success' => true, 'level' => $level->toArray()));
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

