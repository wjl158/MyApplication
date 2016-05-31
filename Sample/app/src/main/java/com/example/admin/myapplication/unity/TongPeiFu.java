package com.example.admin.myapplication.unity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class TongPeiFu {

    public class Animal {
        private String name;

        public Animal(String name) {
            this.name = name;
        }

        public void eat() {
            System.out.println(getName() + " can eat.");
        }

        public String getName(){
            return name;
        }
    }


    public class Cat extends Animal {

        public Cat(String name) {
            super(name);
        }

        public void jump(){
            System.out.println(getName() + " can jump.");
        }
    }


    public class Bird extends Animal {

        public Bird(String name) {
            super(name);
        }

        public void fly(){
            System.out.println(getName() + " can fly.");
        }
    }


    public class Magpie extends Bird {

        public Magpie(String name) {
            super(name);
        }

        public void sing(){
            System.out.println(getName() +
                    " can not only eat,but sing");
        }
    }

    //调用
    public class AnimalTrainer {
        public void act(List<Animal> list) {
            for (Animal animal : list) {
                animal.eat();
            }
        }

        public void act1(List<? extends Animal> list) {
            for (Animal animal : list) {
                animal.eat();
            }
        }
    }


    public void testAdd(List<? extends Animal> list){
        //....其他逻辑
        //list.add(new Animal("animal"));  //无法通过编译
        //list.add(new Bird("bird"));      //无法通过编译
        //list.add(new Cat("cat"));        //无法通过编译

        //这里是因为如果是这样调用的
        //List<Cat> catList = new ArrayList<>();
        // testAdd(catList)
        //那么上面的list.add(new Bird("bird"));  显然new Bird("bird")类型不是Cat，
        //也就是说参数类型不能确定，你不知道testAdd函数里传递的参数是cat\bird\animal
        //但是这样用就没问题:
        // Animal animal =  (Animal)list.get(0);
        // animal.eat();
        // 因为不管通过testAdd函数里传递的参数是cat\bird\animal，都是animal的子类，都有父类eat()函数

    }

    public void testAdd1(List<? super Bird> list){
        list.add(new Bird("bird"));
        list.add(new Magpie("magpie"));

        //看第一段代码，其分析如下，因为”? super Bird”代表了Bird或其父类，而Magpie是Bird的子类，
        // 所以上诉代码不可通过编译。而事实上是”行“，为什么呢？2？

        //在解疑之前，再来强调一个知识点，子类可以指向父类，即Bird也是Animal对象。
        // 现在考虑传入到testAdd（）的所有可能的参数，可以是List<Bird>,List<Animal>,或者List<Objext>等等，
        // 发现这些参数的类型是Bird或其父类，那我们可以这样看，把bird、magpie看成Bird对象，
        // 也可以将bird、magpie看成Animal对象，类似的可看成Object对象，
        // 最后发现这些添加到List<? supe Bird> list里的对象都是同一类对象（如本文刚开篇提到的Test 1），
        // 因此testAdd1方法是符合逻辑，可以通过编译的。：
    }


    public  void exec(){
        AnimalTrainer animalTrainer = new AnimalTrainer();
        //Test 1
        List<Animal> animalList = new ArrayList<>();
        animalList.add(new Cat("cat1"));
        animalList.add(new Bird("bird1"));

        animalTrainer.act(animalList);	//可以通过编译

        //Test 2
        List<Cat> catList = new ArrayList<>();
        catList.add(new Cat("cat2"));
        catList.add(new Cat("cat3"));

        //animalTrainer.act(catList);		//无法通过编译
        //animalTrainer.act1(catList);        //可以通过编译

        //如上，Test 1 的执行应该可以理解的，不过顺带提醒一下的是，因为cat1和bird1都是Animal对象，
        // 自然可以添加List<Animal>里，具体解释可参考Java泛型基础 。
        // 对于Test 2，无法通过编译是因为List<Cat>并不是List<Animal>子类，传入参数有误，也就无法通过编译了。
        // 现在尝试去修改AnimalTrainer.act()代码，让它变得更为通用的一点，即不仅仅是接受List<Animal>参数，
        // 还可以接受List<Bird>等参数。那如何更改呢？？

        //既然知道List<Cat>并不是List<Anilmal>的子类型，那就需要去寻找替他解决的办法，
        // 是AnimalTrianer.act()方法变得更为通用（既可以接受List<Animal>类型，也可以接受List<Cat>等参数）。
        // 在java里解决办法就是使用通配符“？”，具体到AnimalTrianer,就是将方法改为act(List<？ extends Animal> list),
        // 当中“？”就是通配符，而“？ extends Animal”则表示通配符“？”的上界为Animal，
        // 换句话说就是，“？ extends Animal”可以代表Animal或其子类，可代表不了Animal的父类（如Object），
        // 因为通配符的上界是Animal。
        // 改进后为AnimalTrianer.act1,调用animalTrainer.act1(catList);可以编译通过

        List<? extends Animal> list = new ArrayList<>();  //这里为什么可以编译通过?
        //list.add(new Animal("animal"));  //无法通过编译
        //list.add(new Bird("bird"));      //无法通过编译
        //list.add(new Cat("cat"));        //无法通过编译
        //因为List<? extends Animal>的类型“？ extends Animal”无法确定，可以是Animal，Bird或者Cat等，
        // 所以为了保护其类型的一致性，也是不能往list添加任意对象的，不过却可以添加 null。



        List<? super Bird> list1 = new ArrayList<>();
        list1.add(new Bird("bird"));
        list1.add(new Magpie("magpie"));
        //list1.add(new Animal("animal"));  //无法通过编译
        //现在考虑传入到testAdd（）的所有可能的参数，可以是List<Bird>,List<Animal>,或者List<Objext>等等，
        //发现如果传入的是List<Bird>，那么list1.add(new Animal("animal"))报错
    }
}
