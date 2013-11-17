<?php

namespace Progeny\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\JsonModel;

class ProgenyController extends AbstractActionController
{
 protected $facebook = null;
 protected $fb_id = null;

 public function indexAction()
 {
  return new ViewModel();
 }

 public function AddProgenyAction()
 {
  $first_name = $this->params()->fromQuery('first_name');
  $last_name = $this->params()->fromQuery('last_name');
  $age = $this->params()->fromQuery('age');

  $data = array('first_name' => $first_name, 'last_name' => $last_name, 'age' => $age);

  $progeny = $this->getProgenyTable()->newProgeny($data);

  return new JsonModel(array('success' => true));
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

  $this->progenyTable = $this->getServiceLocator()->get('Progeny\Service\ProgenyTable');
  $this->progenyTable->setFacebook($this->getFacebook());
  $this->progenyTable->setFbId($this->getFbId());

  return $this->progenyTable;
 }


}

