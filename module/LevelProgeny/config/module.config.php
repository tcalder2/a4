<?php
return array(
 'doctrine' => array(
  'driver' => array(
   'level_progeny_entities' => array(
    'class' => 'Doctrine\ORM\Mapping\Driver\AnnotationDriver',
    'cache' => 'array',
    'paths' => array(__DIR__ . '/../src/LevelProgeny/Entity')
   ),

   'orm_default' => array(
    'drivers' => array(
     'LevelProgeny\Entity' => 'level_progeny_entities'
    )
   ))),

 'router' => array(
  'routes' => array(

   'savegame' => array(
    'type' => 'Zend\Mvc\Router\Http\Literal',
    'options' => array(
     'route' => '/savegame',
     'defaults' => array(
      'controller' => 'Levelprogeny\Controller\Levelprogeny',
      'action' => 'savegame',
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
   'LevelProgeny\Controller\LevelProgeny' => 'LevelProgeny\Controller\LevelProgenyController'
  ),
 ),


);