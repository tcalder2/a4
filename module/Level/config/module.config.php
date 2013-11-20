<?php
return array(
 'doctrine' => array(
  'driver' => array(
   'level_entities' => array(
    'class' => 'Doctrine\ORM\Mapping\Driver\AnnotationDriver',
    'cache' => 'array',
    'paths' => array(__DIR__ . '/../src/Level/Entity')
   ),

   'orm_default' => array(
    'drivers' => array(
     'Level\Entity' => 'level_entities'
    )
   ))),

 'router' => array(
  'routes' => array(

   'getprogenies' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/getprogenies',
     'defaults' => array(
      'controller' => 'Level\Controller\Level',
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
   'Level\Controller\Level' => 'Level\Controller\LevelController'
  ),
 ),


);