<?php
/**
 * Zend Framework (http://framework.zend.com/)
 *
 * @link      http://github.com/zendframework/ZendSkeletonApplication for the canonical source repository
 * @copyright Copyright (c) 2005-2013 Zend Technologies USA Inc. (http://www.zend.com)
 * @license   http://framework.zend.com/license/new-bsd New BSD License
 */

namespace Application;

use Zend\Mvc\ModuleRouteListener;
use Zend\Mvc\MvcEvent;

class Module
{
 public function onBootstrap(MvcEvent $e)
 {
  $eventManager = $e->getApplication()->getEventManager();
  $moduleRouteListener = new ModuleRouteListener();
  $moduleRouteListener->attach($eventManager);
 }

 public function getConfig()
 {
  return include __DIR__ . '/config/module.config.php';
 }

 public function getAutoloaderConfig()
 {
  return array(
   'Zend\Loader\StandardAutoloader' => array(
    'namespaces' => array(
     __NAMESPACE__ => __DIR__ . '/src/' . __NAMESPACE__,
    ),
   ),
  );
 }


 public function getServiceConfig()
 {
  return array(
   'factories' => array(
    'Application\Service\Facebook' => function () {

      $facebook = new \Facebook(array('appId' => '654412204576554', 'secret' => 'bebd056f6d6ff934cc48e36536b58318'));

      return $facebook;
     },

    'Application\Service\FbId' => function ($sm) {

      if(array_key_exists('fb_test', $_GET) && $_GET['fb_test'])
       return '508430727';


      /** @var \Facebook $facebook */
      $facebook = $sm->get('Application\Service\Facebook');

      $fb_id = $facebook->getUser();

      return $fb_id;
     },

    'Application\Service\FacebookProfile' => function ($sm) {

      /** @var \Zend\ServiceManager\ServiceManager $sm */

      if(array_key_exists('fb_test', $_GET) && $_GET['fb_test'])
       return array(
        "id" => "508430727",
        "name" => "Crystal Keenan",
        "first_name" => "Crystal",
        "last_name" => "Keenan",
        "link" => "https:\/\/www.facebook.com\/bluebaronca",
        "username" => "bluebaronca",
        "gender" => "male",
        "timezone" => -5,
        "locale" => "en_GB",
        "verified" => true,
        "updated_time" => "2013-10-10T16:41:35+0000");

      /** @var \Facebook $facebook */
      $fb_id = $sm->get('Application\Service\FbId');

      return $sm->get('Application\Service\Facebook')->api('/' . $fb_id, 'GET');
     }
   )
  );


 }
}
