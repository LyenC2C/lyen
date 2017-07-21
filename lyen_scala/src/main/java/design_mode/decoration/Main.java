package design_mode.decoration;

/**
 * Created by lyen on 16-9-14.
 */
public class Main {
    /**
     * 装饰者模式可以有效分离 性能组件 和 功能组件
     * ps：委托机制，复用系统中的各个组件，在运行时，可以将这些 功能组件 进行叠加，从而构造一个 “超级对象”，
     *    使其拥有这些组件的功能，而各个 子功能模块 被很好地维护在 各个组件 的相关 类 中，拥有整洁的系统结构
     *
     * 装饰者  PacketDecorator 维护一个装饰对象 IPacketCreator component
     * 被装饰者 PacketBodyCreator
     *
     */
    public static void main(String[] args) {
        /**
         * 典型的装饰模式  -> 数据输入，输出流
         *
         *通过装饰者的构造函数，将被装饰的对象传入
         */
        IPacketCreator pc = new PacketHTTPHeaderCreator(new PacketHTMLHeaderCreator(new PacketBodyCreator()));
        System.out.println(pc.handleContent());

    }
}
