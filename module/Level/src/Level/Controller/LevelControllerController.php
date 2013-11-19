<?php

namespace Level\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;

class LevelControllerController extends AbstractActionController
{

    public function indexAction()
    {
        return new ViewModel();
    }


}

