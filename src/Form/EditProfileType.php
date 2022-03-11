<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;

class EditProfileType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder



            ->add('nom', TextType::class, [
                'attr' => ['class' => 'form-control'
                ],
                'empty_data' => ''

            ])
            ->add('prenom', TextType::class, [
                'attr' => ['class' => 'form-control'],
                'empty_data' => ''

            ])
            ->add('adresse', TextType::class, [
                'attr' => ['class' => 'form-control'],
                'empty_data' => ''

            ])
            ->add('numTel', TextType::class, [
                'attr' => ['class' => 'form-control'],
                'constraints' => [

                    new Length([
                        'min' => 8,
                        'minMessage' => 'Le numero de telephone doit contenir 8 digit',
                        // max length allowed by Symfony for security reasons
                        'max' => 8,
                    ]),
                ],
                'empty_data' => ''

            ])

            ->add('email', TextType::class, [
                'attr' => ['class' => 'form-control'],
                'empty_data' => ''

            ])
            ->add('image',FileType::class, [     "attr" => [
                'class' => 'form-control',
            ],
                'label' => false,
                'multiple' => false,
                'mapped' => false,
                'required' => true
            ])
            ->add('Modifier', SubmitType::class, [
                "attr" => [
                    "class" => "mt-5 btn_1 gradient",
                ]

            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
