<?php

namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\RangeType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre',null,['required' =>false])
            ->add('description',null,['required' =>false])
            ->add('foodqulaite',RangeType::class, ['attr' => [
                'min' => 1,
                'max' => 10
            ],])
            ->add('price',RangeType::class,  ['attr' => [
                'min' => 1,
                'max' => 10
            ],])
            ->add('service',RangeType::class,  ['attr' => [
                'min' => 1,
                'max' => 10,


            ],])
            ->add('image', FileType::class, [
                'label' =>false,
                'multiple'=>false,
                'mapped' => false,
                'required' => false,


            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
