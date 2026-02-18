package com.lxhcat.lxhcatlist;

public class LXHCatList <E>{

    /*
        方法说明：
        public LXHCatList() 无参构造
        public LXHCatList(int initialCapacity) 有参构造
        private void grow() 扩容
        public void add(E e) 增加 e: 要增加的元素
        public void remove(int index) 删除 index: 要删除的元素所对应的数组下标
        public void set(int replaceTargetIndex, E e) 编辑，也可以叫覆盖 replaceTargetIndex: 想要覆盖的元素对应下标 e: 要替换的元素
        public int indexOf(E e) 查找元素在数组中对应的下标，返回下标值 e: 要查找的元素
        public E get(int index) 获取数组对应下标内存储的元素，返回元素(地址值) index: 获取的元素在数组中对应的下标
        public boolean contains(E e) 查找数组内是否包含某个元素，返回布尔素(是否包含) e: 需要查找的元素
        public void clear() 清空数组
        public int capacity() 获取容量，返回容量
        public int size() 获取长度，返回长度
        public boolean isEmpty() 判断集合是否是空集合，返回布尔素(是否为空)

        本类中认为的两类变量名含义说明：
        capacity: 本类中记为可用长度（容量），如给定了初始化长度为5，但未存储任何元素，此时capacity仍为5
        size: 本类中记为长度，和上述capacity不同，只算存储的元素数量，如使用上述例子，则capacity=5，size=0
    */
    private E[] elements; // E: 泛型
    private static final int defaultCapacity = 10; // 如果创建的实例化对象没有给定容量，则使用此常量作为默认容量
    private int capacity; // 容量
    private int size = 0; // 长度


    // 将一个 Object 类型的数组强制转换为泛型类型 E 的数组是不安全的操作，因为在运行时可能会出现类型转换异常。
    // 所以默认情况下，如果出现类似(E[]) new Object[initialSize]这种强转，就会出现警告：Unchecked cast: 'java.lang.Object[]' to 'E[]'
    // 为了解决这个问题，我们可以采取抑制警告来解决，我们可以使用@SuppressWarnings("unchecked")这个注解来抑制这个警告
    // 使用 @SuppressWarnings("unchecked") 注解就是告诉编译器，我们开发者已经确认这里的转换在当前场景下是合理的，让它不要显示这个警告。
    @SuppressWarnings("unchecked")
    public LXHCatList(){ // 无参
        // (E[])实际上本质是一种强转，和(int)(double)这种是一样的
        // 所以这行代码的意思就是把这个Object类型的数组(也就是new Object[defaultCapacity])强转成E[](泛型的数组)，然后赋值给elements
        // 创建完成后，要把刚才创建的数组的地址值((E[]) new Object[initialCapacity])赋值给elements
        elements = (E[]) new Object[defaultCapacity]; // 无参，所以使用默认初始化容量
        capacity = defaultCapacity; // 创建完成后，要把defaultCapacity赋值给capacity，这样，capacity(容量变量)就得到了更新
    }
    @SuppressWarnings("unchecked")
    public LXHCatList(int initialCapacity){ // 有参
        if(initialCapacity < 0){ // 如果范围超出合法范围，直接抛出异常
            throw new IllegalArgumentException("Cannot create a LXHCatList with the illegal size provided: " + initialCapacity);
        }else{
            elements = (E[]) new Object[initialCapacity]; // 创建完成后，要把刚才创建的数组的地址值((E[]) new Object[initialCapacity])赋值给elements
            capacity = initialCapacity; // 创建完成后，要把initialCapacity赋值给capacity，这样，capacity(容量变量)就得到了更新
        }
    }

    @SuppressWarnings("unchecked")
    private void grow(){ // 扩容
        // int newCapacity = capacity + (capacity >> 1); // ">>"的数学意义：把数字乘0.5倍，所以这行代码意思是cpacity + 0.5倍的capacity，加起来也就是1.5倍的capacity
        // int newCapacity = (int) (capacity * 1.5); // 但这里也可以直接用"*"代替
        int newCapacity = (capacity == 1 || capacity == 0) ? (capacity + 1) : ((int) (capacity * 1.5)); // 再加上考虑容量为0或1的情况
        Object[] newArray = new Object[newCapacity]; // 创建一个新Object数组，之后会用到，因为这是所有类的父类
        // System.arraycopy参数说明(从前往后依次说明)：
        // src:要复制的源数组，srcPos:源数组中要复制的起始位置，dest:目标数组(用于接收复制的元素)，destPos:目标数组中开始粘贴的起始位置，length:要复制的元素数量
        System.arraycopy(elements, 0, newArray, 0, elements.length);

        elements = (E[]) newArray; // 复制完成后，要把新数组的地址值((E[]) newArray)赋值给旧数组的变量名(elements)
        capacity = newCapacity; // 所有操作完成后，要把扩容后的容量赋值给capacity，让capacity(容量变量)的值更新
    }

    // 增加 e: 要增加的元素
    public void add(E e){
        if(capacity == size()){ // 如果长度=容量了，那么就代表数组满了，则进入扩容
            grow();
        }
        elements[size()] = e; // 在尾部添加这个元素
        size++; // 增加完成后，更新size的值
    }

    // 删除 index: 要删除的元素所对应的数组下标
    public void remove(int index){
        if(index < 0 || index >= size()){ // 如果范围超出合法范围，直接抛出异常
            throw new IndexOutOfBoundsException("Cannot find the element that need to delete with the illegal index provided: " + index);
        }
        int i;
        for(i = index; i < size() - 1; i++){ // 再从[删除的下标+1]存储的元素开始一直到最后一个非null元素，这个范围整体通过遍历一个一个向前移
            elements[i] = elements[i + 1]; // 也就是从被删除的下标开始(i)，每次都把后一个元素的下标(i+1)所存储的元素往前移一位(也就是一个个的赋值)
        }
        elements[size() - 1] = null; // 处理最后一个尾部元素，因为删除后整体向前移了，但最后一个并没能被遍历操作所处理，所以这个删除前的最后一个元素要赋值变成null
        size--; // 删除完成后，更新size的值
    }

    // 编辑，也可以叫覆盖 replaceTargetIndex: 想要覆盖的元素对应下标 e: 要替换的元素
    public void set(int replaceTargetIndex, E e){
        if(replaceTargetIndex < 0 || replaceTargetIndex >= size()){ // 如果范围超出合法范围，直接抛出异常
            throw new IndexOutOfBoundsException("Cannot edit(cover) the element with the illegal index provided: " + replaceTargetIndex);
        }
        elements[replaceTargetIndex] = e; // 直接覆盖，也就是重新赋值
    }

    // 查找元素在数组中对应的下标，返回下标值 e: 要查找的元素
    public int indexOf(E e){
        for (int i = 0; i < size(); i++) { // 遍历检查是否包含，包含则返回对应元素下标值
            if(Objects.equals(elements[i], e)){ // 使用Object.equals方法对比是否相等不会出现空指针异常（支持null）
                return i;
            }
        }
        return -1; // 否则返回-1，表示未能查找到
    }

    // 获取数组对应下标内存储的元素，返回元素(地址值) index: 获取的元素在数组中对应的下标
    public E get(int index){
        if(index < 0 || index >= size()){ // 如果范围超出合法范围，直接抛出异常
            throw new IndexOutOfBoundsException("Cannot get the element with the illegal index provided: " + index);
        }
        return elements[index]; // 返回元素
    }

    // 查找数组内是否包含某个元素，返回布尔素(是否包含) e: 需要查找的元素
    public boolean contains(E e){
        for (int i = 0; i < size(); i++) { // 遍历检查是否包含，包含则返回true
            if (Objects.equals(elements[i], e)) { // 使用Object.equals方法对比是否相等不会出现空指针异常（支持null）
                return true;
            }
        }
        return false; // 遍历都走完了还没有满足if语句，自然就确认不包含了，所以返回false
    }

    // 清空数组
    public void clear(){
        for (int i = 0; i < size; i++) { // 遍历清空数组
            if(elements[i] != null){
                elements[i] = null;
            }
        }
        size = 0;  // 清空数组后把长度更新为0
    }

    // 获取容量，返回容量
    public int capacity() {
        return capacity;
    }

    // 获取长度，返回长度
    public int size(){
        return size; // 返回size
    }

    // 判断集合是否是空集合，返回布尔素(是否为空)
    public boolean isEmpty(){
        return size == 0; // 判断size是否为0
    }

}


