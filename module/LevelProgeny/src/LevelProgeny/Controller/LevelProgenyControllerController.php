<?php

namespace LevelProgeny\Controller;

use Zend\Mvc\Controller\AbstractActionController;
use Zend\View\Model\ViewModel;

class LevelProgenyController extends AbstractActionController
{

    public function indexAction()
    {
        return new ViewModel();
    }


}

