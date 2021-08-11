package com.hlju.version7.common.codeco;

import java.io.*;

/**
 * java原生的序列化方式
 */
public class ObjectSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
//        System.out.println("开始编码：" + obj);
        byte[] bytes = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 将对象写入到输出流中
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();

            // 从流中获取字节数组
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (bos != null) {
                    bos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    /**
     * 反序列化
     * @param bytes
     * @param messageType Object原生的反序列化用不到messageType参数
     * @return
     */
    @Override
    public Object deSerialize(byte[] bytes, int messageType) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            o = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("解码后结果：" + o);
        return o;
    }

    @Override
    public Integer getType() {
        return Serializer.OBJECT_SERIALIZER_TYPE;
    }
}
