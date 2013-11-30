<?php

namespace LevelProgeny\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\JsonModel;
use Zend\View\Model\ViewModel;

class LevelProgenyController extends AbstractActionController
{
 protected $facebook = null;
 protected $fb_id = null;
 protected $level_progeny_table;
 protected $progeny_table;

 public function indexAction()
 {
  return new ViewModel();
 }

 public function SaveGameAction()
 {
  $progeny_id = $this->params()->fromQuery('progeny_id');
  $progeny = $this->getProgenyTable()->getProgeny($progeny_id);

  $time = $this->params()->fromQuery('time');
  $time_validator = new \Zend\Validator\Digits();

  $mistakes = $this->params()->fromQuery('mistakes');
  $mistakes_validator = new \Zend\Validator\Digits();

  $level = $this->params()->fromQuery('level');
  $level_validator = new \Zend\Validator\Digits();

  if (!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that ID.'));

  if (!$mistakes_validator->isValid($mistakes))
   return new JsonModel(array('success' => false, 'messages' => $mistakes_validator->getMessages()));

  if (!$time_validator->isValid($time))
   return new JsonModel(array('success' => false, 'messages' => $time_validator->getMessages()));

  if (!$level_validator->isValid($level))
   return new JsonModel(array('success' => false, 'messages' => $level_validator->getMessages()));

  /** @var \LevelProgeny\Service\LevelProgenyTable $level_progeny_table */
  $level_progeny_table = $this->getLevelProgenyTable();
  $level_progeny_table->saveGame($time, $mistakes, $progeny, $level);

  return new JsonModel(array('success' => true, 'progeny' => $this->getProgenyTable()->getProgenyDataArray($progeny)));
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
  * @return \LevelProgeny\Service\LevelProgenyTable
  */
 public function getLevelProgenyTable()
 {
  if ($this->level_progeny_table) return $this->level_progeny_table;

  $this->level_progeny_table = $this->getServiceLocator()->get('LevelProgeny\Service\LevelProgenyTable');

  return $this->level_progeny_table;
 }

 /**
  * @return \Progeny\Service\ProgenyTable
  */
 public function getProgenyTable()
 {
  if ($this->progeny_table) return $this->progeny_table;

  $this->progeny_table = $this->getServiceLocator()->get('Progeny\Service\ProgenyTable');

  return $this->progeny_table;
 }


}

