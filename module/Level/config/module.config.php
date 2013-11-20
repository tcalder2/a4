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

   'changemistakesallowed' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/changelevelmistakesallowed',
     'defaults' => array(
      'controller' => 'Level\Controller\Level',
      'action' => 'changemistakesallowed',
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