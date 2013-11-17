<?php

namespace Progeny\Controller;

use Progeny\Entity\Progeny;
use Zend\Config\Writer\Json;
use Zend\Mvc\Controller\AbstractActionController;
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

 public function AddProgenyAction()
 {
  $first_name = $this->params()->fromQuery('first_name');
  $birth_date = $this->params()->fromQuery('birth_date');

  $birth_date_validator = Progeny::getBirthDateValidator();
  $first_name_validator = Progeny::getFirstNameValidator();

  if (!$birth_date_validator->isValid($birth_date))
   return new JsonModel(array('success' => false, 'messages' => $birth_date_validator->getMessages()));

  if(!$first_name_validator->isValid($first_name))
   return new JsonModel(array('success' => false, 'messages' => $first_name_validator->getMessages()));

  if (!$this->getProgenyTable()->checkFirstNameUnique($first_name))
   return new JsonModel(array('success' => false, 'message' => 'You have already added a child with this name'));

  $data = array('first_name' => $first_name, 'birth_date' => $birth_date);

  $progeny = $this->getProgenyTable()->newProgeny($data);

  return new JsonModel(array('success' => true, $progeny->toArray()));
 }

 public function getProgeniesAction()
 {
  return new JsonModel(array('success' => true, 'progenies' => $this->getProgenyTable()->getProgeniesArray()));
 }

 public function getProgenyAction()
 {
  $id = $this->params()->fromQuery('id');

  return new JsonModel(array($this->getProgenyTable()->getProgeny()->toArray()));
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
  if ($this->progenyTable) return $this->progenyTable;

  $this->progeny_table = $this->getServiceLocator()->get('Progeny\Service\ProgenyTable');

  return $this->progeny_table;
 }


}

