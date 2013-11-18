<?php
namespace User;

class Module
{
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
    'User\Service\User' => function ($sm) {
       /** @var \Zend\ServiceManager\ServiceManager $sm */
       $fb_id = $sm->get('Application\Service\FbId');
       $em = $sm->get('Doctrine\ORM\EntityManager');
       return $em->getRepository('User\Entity\User')->findOneBy(array('fb_id' => $fb_id));
      },

    'User\Service\UserTable' => function ($sm) {
       $table = new \User\Service\UserTable($sm);
       return $table;
      },

   )
  );
 }
}
