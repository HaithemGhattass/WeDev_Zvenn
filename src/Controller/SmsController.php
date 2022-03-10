<?php
namespace App\Controller;
use App\Entity\User;
use http\Env\Response;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Twilio\Rest\Client;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;

class SmsController extends AbstractController
{
    /**
     * @Route("/sendSms", name="SendSms")
     * @throws \Twilio\Exceptions\ConfigurationException
     */
    public function sms()
    {
        $this->getUser()->setPhoneCode(rand(1000,9999));
        $em = $this->getDoctrine()->getManager();
        $em->flush();
// Your Account SID and Auth Token from twilio.com/console
        $sid = 'ACc11d95996ceef478537fe34c64ce8825';
        $token = '7b45dfd7067275b65fbfb09d77bd1b74';
        $client = new Client($sid, $token);

// Use the client to do fun stuff like send text messages!
        $client->messages->create(
// the number you'd like to send the message to
            "+216".$this->getUser()->getNumTel(),
            [
                // A Twilio phone number you purchased at twilio.com/console
                'from' => '+19412691004',
                // the body of the text message you'd like to send
                'body' => $this->getUser()->getPhoneCode()
            ]
        );
        return $this->redirectToRoute("verifsms");

    }

}