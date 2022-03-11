<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraint;
use Symfony\Component\Validator\Constraints\IsTrue;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type as Form;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

class RegistrationFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('nom', TextType::class, [
                'attr' => ['class' => 'form-control'],

            ])
            ->add('prenom', TextType::class, [
                'attr' => ['class' => 'form-control'],
            ])
            ->add('email', TextType::class, [
                'attr' => ['class' => 'form-control'],

            ])
            ->add('adresse', TextType::class, [
                'attr' => ['class' => 'form-control'],
            ])
            ->add('numTel', TextType::class, [
                'constraints' => [

                    new Length([
                        'min' => 8,
                        'minMessage' => 'Le numero de telephone doit contenir 8 digit',
                        // max length allowed by Symfony for security reasons
                        'max' => 8,
                    ]),
                ],
                'empty_data' => '',
                'attr' => ['class' => 'form-control'],
            ])
            ->add('pseudo', TextType::class, [
                'attr' => ['class' => 'form-control'],
            ])



            ->add('sexe',ChoiceType::class, [
                'choices'  => [
                    'homme' => 'homme',
                    'femme' => 'femme',


                ],
                    'attr' => ['class' => 'form-control'],
            ])
            ->add('dateNaissance',DateType::Class, array(


                'years' => range(date('Y')-50, date('Y')+50),

            ))
            ->add('agreeTerms', CheckboxType::class, [
                'attr' => ['class' => 'form-check-input'],
                'mapped' => false,
                'constraints' => [
                    new IsTrue([
                        'message' => 'You should agree to our terms.',
                    ]),
                ],
            ])
            ->add('plainPassword', RepeatedType::class, [





                'type' => PasswordType::class,
                'invalid_message' => 'mot de passe n est sont pas identique ',
                'options' => ['attr' => ['class' => 'form-control']],
                'required' => true,
                'first_options'  => ['label' => false],
                'second_options' => ['label' => false],

                'mapped' => false,
                'attr' => ['autocomplete' => 'new-password'],

                'constraints' => [
                    new NotBlank([
                        'message' => 'il faut remplir le champs mot de passe',
                    ]),
                    new Length([
                        'min' => 6,
                        'minMessage' => 'ton mot de passe doit avoir 6 characters minimum',
                        // max length allowed by Symfony for security reasons
                        'max' => 4096,
                    ]),
                ],
            ])
        ;
    }


    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
