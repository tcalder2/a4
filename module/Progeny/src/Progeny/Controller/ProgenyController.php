<?php

namespace Progeny\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\JsonModel;

class ProgenyController extends AbstractActionController
{

    public function indexAction()
    {
        return new ViewModel();
    }

    public function AddChildAction()
    {
        return new JsonModel(array('success' => true));
    }


}

