<?php
return array(
 'doctrine' => array(
  'driver' => array(
   'progeny_entities' => array(
    'class' => 'Doctrine\ORM\Mapping\Driver\AnnotationDriver',
    'cache' => 'array',
    'paths' => array(__DIR__ . '/../src/Progeny/Entity')
   ),

   'orm_default' => array(
    'drivers' => array(
     'Progeny\Entity' => 'progeny_entities'
    )
   ))),

 'router' => array(
  'routes' => array(

   'addprogeny' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/addprogeny',
     'defaults' => array(
      'controller' => 'Progeny\Controller\Progeny',
      'action' => 'addprogeny',
     ),
    ),
   ),

   'changeprogenybirthdate' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/changeprogenybirthdate',
     'defaults' => array(
      'controller' => 'Progeny\Controller\Progeny',
      'action' => 'changebirthdate',
     ),
    ),
   ),

   'removeprogeny' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/removeprogeny',
     'defaults' => array(
      'controller' => 'Progeny\Controller\Progeny',
      'action' => 'removeprogeny',
     ),
    ),
   ),

   'changeprogenytimeallowed' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/changeprogenytimeallowed',
     'defaults' => array(
      'controller' => 'Progeny\Controller\Progeny',
      'action' => 'changeprogenytimeallowed',
     ),
    ),
   ),

   'getprogenies' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/getprogenies',
     'defaults' => array(
      'controller' => 'Progeny\Controller\Progeny',
      'action' => 'getprogenies',
     ),
    ),
   ),


  ),
 ),

 'view_manager' => array(
  'template_path_stack' => array(
   'page' => __DIR__ . '/../view',
  ),
  'strategies' => array(
   'ViewJsonStrategy',
  ),
 ),

 'controllers' => array(
  'invokables' => array(
   'Progeny\Controller\Progeny' => 'Progeny\Controller\ProgenyController'
  ),
 ),


);