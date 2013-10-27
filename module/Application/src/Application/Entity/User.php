<?php

namespace Application\Entity;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Zend\InputFilter\Factory as InputFactory;
use Zend\InputFilter\InputFilter;
use Zend\InputFilter\InputFilterAwareInterface;
use Zend\InputFilter\InputFilterInterface;

/**
 * @ORM\Entity
 * @ORM\Table(name="user")
 */
class User {

 protected $inputFilter;


 function __construct()
 {
  $this->is_parent = false;
 }

 public function toArray()
 {
  return array(
   'email' => $this->getEmail(),
   'is_registered' => $this->getIsRegistered(),
  );


 }

 public function setInputFilter(InputFilterInterface $i)
 {
  throw new \Exception("Not used");
 }


 public function getInputFilter()
 {
  if($this->inputFilter) return $this->inputFilter;

  $inputFilter = new InputFilter();
  $factory = new InputFactory();

  //email
  $inputFilter->add($factory->createInput(array(
   'name' => 'email',
   'required' => true,
   'filters' => array(
    array('name' => 'StripTags'),
    array('name' => 'StringTrim'),
   ),
   'validators' => array(
    array(
     'name' => 'EmailAddress',
    ),
    array(
     'name' => 'StringLength',
     'options' => array(
      'encoding' => 'UTF-8',
      'min' => 1,
      'max' => 255,
     ),
    ),
   ),
  )));

  //password
  $inputFilter->add($factory->createInput(array(
   'name' => 'password',
   'required' => true,
   'filters' => array(
    array('name' => 'StripTags'),
    array('name' => 'StringTrim'),
   ),
   'validators' => array(
    array(
     'name' => 'StringLength',
     'options' => array(
      'encoding' => 'UTF-8',
      'min' => 8,
      'max' => 50,
     ),
    ),
   ),
  )));


  $this->inputFilter = $inputFilter;

  return $this->inputFilter;
 }

 /**
  * @ORM\Id
  * @ORM\GeneratedValue(strategy="AUTO")
  * @ORM\Column(type="integer")
  */
 protected $id;

 /** @ORM\Column(type="string", nullable=true) */
 protected $username;

 /** @ORM\Column(type="string", nullable=true) */
 protected $fb_id;

 /** @ORM\Column(type="string", nullable=true) */
 protected $first_name;

 /** @ORM\Column(type="string", nullable=true) */
 protected $last_name;

 /** @ORM\Column(type="string", nullable=true) */
 protected $fb_link;

 //Parent mode must have a password on it. The password should be cs2212 initially. Then he/she should be able to change the password to one that is case sensitive and 3-6 characters long.
 /** @ORM\Column(type="string", nullable=true) */
 protected $password;

//Initially the Facebook user should also be forced to answer one of 3 pre-set questions such as "What is your mother's maiden name". The Facebook user can pick which of the 3 questions to answer. The answers should NOT be case sensitive. The answer can not be longer than 30 characters and must be at least 1 character. Your group can pick what 3 questions to show to the parent.
//If the user types in the wrong password more than 3 times, then your system should display the question that was answered by the user. If the user gets the answer correct, allow him/her to reset the password to something new, otherwise set the password back to cs2212
 /** @ORM\Column(type="string", nullable=true) */
 protected $question;

 //A user must be able to choose between three skins on the Drill Mode. A skin is a look and feel and/or a theme for the interface. For example all the buttons/text/menus would have the same colour scheme in a particular skin. 3% of your final mark for acceptance testing will be based on the aesthetic appeal/creativity/originality of your skins. HINT: Java swing has a look and feel class to help. If you want to do some cool stuff, do a search on Java, "Look and Feel" for sample code. BUT LEAVE THIS TO THE END, make sure all the other stuff works first :-)
 /** @ORM\Column(type="string", nullable=true) */
 protected $skin;


 public function exchangeArray($data)
 {
  $this->email = (isset($data['email'])) ? $data['email'] : null;
  $this->password = (isset($data['password'])) ? $data['password'] : null;
  $this->first_name = (isset($data['first_name'])) ? $data['first_name'] : null;
  $this->last_name = (isset($data['last_name'])) ? $data['last_name'] : null;
  $this->fb_id = (isset($data['id'])) ? $data['id'] : null;
  $this->fb_link = (isset($data['fb_link'])) ? $data['fb_link'] : null;
 }


}

