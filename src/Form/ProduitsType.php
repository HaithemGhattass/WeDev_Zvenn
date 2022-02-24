<?php

namespace App\Form;

use App\Entity\Produits;
use App\Entity\Restaurant;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ProduitsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomProduit')
            ->add('DescriptionProduit')
            ->add('imageProduit',FileType::class, [
                'label' => false,
                'multiple' => false,
                'mapped' => false,
                'required' => true
            ])
            ->add('prixProduit')

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produits::class,
        ]);
    }
}
