<?php

namespace Progeny\Controller;

use Progeny\Entity\Progeny;
use Zend\Config\Writer\Json;
use Zend\Db\Sql\Ddl\Column\Date;
use Zend\Mvc\Controller\AbstractActionController;
use Zend\Validator\Between;
use Zend\Validator\Digits;
use Zend\View\Model\JsonModel;

class ProgenyController extends AbstractActionController
{
 protected $facebook = null;
 protected $fb_id = null;
 protected $progeny_table;

 public function indexAction()
 {
  return new ViewModel();
 }

 public function RemoveProgenyAction()
 {
  $progeny = $this->getProgenyTable()->getProgeny($this->params()->fromQuery('progeny_id'));
  if (!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that id'));

  $this->getProgenyTable()->removeProgeny($progeny);

  return new JsonModel(array('success' => true));
 }

 public function ChangeBirthDateAction()
 {
  $progeny_id = $this->params()->fromQuery('progeny_id');
  $birth_date = $this->params()->fromQuery('birth_date');

  $birth_date_validator = Progeny::getBirthDateValidator();

  if (!$birth_date_validator->isValid($birth_date))
   return new JsonModel(array('success' => false, 'messages' => $birth_date_validator->getMessages()));

  $progeny_table = $this->getProgenyTable();
  $progeny = $progeny_table->getProgeny($progeny_id);
  $progeny_table->updateBirthDate($progeny, date_create($birth_date));

  return new JsonModel(array('success' => true, 'progeny' => $this->getProgenyTable()->getProgenyDataArray($progeny)));
 }

 public function AddProgenyAction()
 {
  $first_name = $this->params()->fromQuery('first_name');
  $birth_date = $this->params()->fromQuery('birth_date');
  $time_allowed = $this->params()->fromQuery('time_allowed');

  $birth_date_validator = Progeny::getBirthDateValidator();
  $first_name_validator = Progeny::getFirstNameValidator();
  $time_allowed_validator = Progeny::getTimeAllowedValidator();

  if (!$birth_date_validator->isValid($birth_date))
   return new JsonModel(array('success' => false, 'messages' => $birth_date_validator->getMessages()));

  if(!$first_name_validator->isValid($first_name))
   return new JsonModel(array('success' => false, 'messages' => $first_name_validator->getMessages()));

  if (!$time_allowed_validator->isValid($time_allowed))
   return new JsonModel(array('success' => false, 'messages' => $time_allowed_validator->getMessages()));

  if (!$this->getProgenyTable()->checkFirstNameUnique($first_name))
   return new JsonModel(array('success' => false, 'message' => 'You have already added a child with this name'));

  $data = array('first_name' => $first_name, 'birth_date' =>  date_create($birth_date), 'time_allowed' => $time_allowed, 'level' => 1);
  $progeny_table = $this->getProgenyTable();
  $progeny = $progeny_table->newProgeny($data);

  return new JsonModel(array('success' => true, 'progeny' => $progeny_table->getProgenyDataArray($progeny)));
 }

 public function saveFinalGameAction()
 {
  $score = $this->params()->fromQuery('score');
  $score_validator = new Digits();

  if(!$score_validator->isValid($score))
   return new JsonModel(array('success' => false, 'messages' => $score_validator->getMessages()));

  $progeny_table = $this->getProgenyTable();
  $progeny = $progeny_table->getProgeny($this->params()->fromQuery('progeny_id'));

  if(!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that ID'));

  $progeny_table->saveFinalGame($progeny, $score);

  return new JsonModel(array('success' => true, 'progeny' => $progeny_table->getProgenyDataArray($progeny)));
 }

 public function changeTimeAllowedAction()
 {
  $progeny_table = $this->getProgenyTable();

  $progeny = $progeny_table->getProgeny($this->params()->fromQuery('progeny_id'));

  if (!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that id'));

  $time_allowed = $this->params()->fromQuery('time_allowed');
  $time_allowed_validator = Progeny::getTimeAllowedValidator();

  if (!$time_allowed_validator->isValid($time_allowed))
   return new JsonModel(array('success' => false, 'messages' => $time_allowed_validator->getMessages()));

  $progeny_table->setTimeAllowed($progeny, (int)$time_allowed);

  return new JsonModel(array('success' => true, 'progeny' => $progeny_table->getProgenyDataArray($progeny)));
 }

 public function ChangeLevelAction()
 {
  $level = $this->params()->fromQuery("level");
  $level_validator_chain = new \Zend\Validator\ValidatorChain();

  $level_validator_chain->attach(new Digits());
  $level_validator_chain->attach(new Between(array('inclusive' => true, 'min' => 1, 'max' => 12)));

  $progeny = $this->getProgenyTable()->getProgeny($this->params()->fromQuery('progeny_id'));

  if (!$level_validator_chain->isValid($level))
   return new JsonModel(array('success' => false, 'messages' => $level_validator_chain->getMessages()));

  if(!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that ID.'));

  $this->getProgenyTable()->setLevel($progeny, $level);

  return new JsonModel(array('success' => true, 'progeny' => $this->getProgenyTable()->getProgenyDataArray($progeny)));
 }

 public function getProgenyAction()
 {
  $progeny = $this->getProgenyTable()->getProgeny($this->params()->fromQuery('progeny_id'));

  if (!$progeny)
   return new JsonModel(array('success' => false, 'message' => 'Could not find a progeny with that id'));

  return new JsonModel(array('success' => true, 'progeny' => $this->getProgenyTable()->getProgenyDataArray($progeny)));
 }

 public function getProgeniesAction()
 {
  return new JsonModel(array('success' => true, 'progenies' => $this->getProgenyTable()->getProgeniesArray()));
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
  * @return \Progeny\Service\ProgenyTable
  */
 public function getProgenyTable()
 {
  if ($this->progeny_table) return $this->progeny_table;

  $this->progeny_table = $this->getServiceLocator()->get('Progeny\Service\ProgenyTable');

  return $this->progeny_table;
 }


}

