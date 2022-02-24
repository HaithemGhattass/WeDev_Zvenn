<?php

namespace App\Form;

use App\Entity\Restaurant;
use Symfony\Component\DomCrawler\Field\TextareaFormField;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

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
                    'dessert' => 'dessert',
                    'starters' => 'starters',
                    'fastfood' => 'fastfood',

                ]])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Restaurant::class,
        ]);
    }
}
