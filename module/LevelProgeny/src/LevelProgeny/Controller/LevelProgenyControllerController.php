<?php

namespace LevelProgeny\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;

class LevelProgenyControllerController extends AbstractActionController
{

    public function indexAction()
    {
        return new ViewModel();
    }


}

