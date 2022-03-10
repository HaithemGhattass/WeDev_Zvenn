<?php

namespace App\Form;

use App\Entity\Restaurant;
use Symfony\Component\DomCrawler\Field\TextareaFormField;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Captcha\Bundle\CaptchaBundle\Form\Type\CaptchaType;
use Captcha\Bundle\CaptchaBundle\Validator\Constraints\ValidCaptcha;

class RestaurantType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('nomRestaurant')

            ->add('addresse')
            ->add('cite')
            ->add('codePostal')

            ->add('heureOuverture')
            ->add('heureFermeture')

            ->add('description')
            ->add('imageRestaurant',FileType::class, [
                'label' => false,
                'multiple' => false,
                'mapped' => false,
                'required' => true
            ])
            ->add('categorieRestaurant',ChoiceType::class, [
                'choices'  => [
                    'chinois' => 'chinois',
                    'mexican' => 'mexican',
                    'fastfood' => 'fastfood',
                    'italien' => 'italien',
                    'pizzareya' => 'pizzareya',

                ]])
            ->add('captchaCode', CaptchaType::class, array(
                'captchaConfig' => 'ExampleCaptchaUserRegistration',
                'constraints' => [
                    new ValidCaptcha([
                        'message' => 'Invalid captcha, please try again',
                    ]),
                ],
            ))

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Restaurant::class,
        ]);
    }
}
