package com.ylt.wechat.utils; 
import com.alibaba.fastjson.JSON;
//import com.cumt.framework.config.ConfigHelper;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis������
 * Created by caicf on 2017/1/1.
 */
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    //Ĭ�ϻ���ʱ��
    private static final int EXPIRE = 60000;

    private static Properties properties;

    private static RedisUtil instance;

    private static JedisPool jedisPool;

    private static ReentrantLock lock = new ReentrantLock();

    private RedisUtil() {
    }

    public static RedisUtil getInstance() {
        if (instance == null) {
            lock.lock();
                if (instance == null) {
                    instance = new RedisUtil();
                }
            lock.unlock();
        }
        return instance;
    }


    /**
     * ��ʼ��JedisPool
     */
    private void initJedisPool() {
    	/*
        properties =ConfigHelper.loadProperties("config.properties");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.valueOf(properties.getProperty("redis.pool.maxIdle")));
        config.setTestOnBorrow(Boolean.getBoolean(properties.getProperty("redis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.getBoolean(properties.getProperty("redis.pool.testOnReturn")));
        jedisPool = new JedisPool(
                config,
                properties.getProperty("redis.ip"), Integer.valueOf(properties.getProperty("redis.port"))
        );
        */
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(true);
        jedisPool = new JedisPool(config,"r-2ze11122a9243ae4.redis.rds.aliyuncs.com",6379,3000,"Yanglt6952339");
    }

    /**
     * ͨ�÷�������JedisPool�л�ȡJedis
     *
     * @return
     */
    private Jedis getJedis() {
        if (jedisPool == null) {
            lock.lock();    //��ֹ�Գ�ʼ��ʱ���߳̾�������
            initJedisPool();
            lock.unlock();
            log.info("JedisPool init success��");
        }
        return jedisPool.getResource();
    }

    /**
     * ͨ�÷������ͷ�Jedis
     *
     * @param jedis
     */
    private void closeJedis(Jedis jedis) {
        jedis.quit();
    }

//===========================================================
    /**
     * ��Keys,�Լ��洢�ṹΪString��List��Set��HashMap���͵Ĳ���
     */
    private final Keys keys = new Keys();
    private final Strings strings = new Strings();
    private final Lists lists = new Lists();
    private final Sets sets = new Sets();
    private final Hash hash = new Hash();
    private final SortSet sortset = new SortSet();

    public Keys keys() {
        return keys;
    }

    public Strings strings() {
        return strings;
    }

    public Lists lists() {
        return lists;
    }

    public Sets sets() {
        return sets;
    }

    public Hash hash() {
        return hash;
    }

    public SortSet sortSet() {
        return sortset;
    }
    //===========================================================

    //*******************************************Keys*******************************************//  
    public class Keys {

        /**
         * ���ù���ʱ��
         *
         * @param key
         * @param seconds
         * @return ����Ӱ��ļ�¼��
         */
        public long expire(String key, int seconds) {
            if (seconds <= 0) {
                return -1L;
            }
            Jedis jedis = getJedis();
            long result = jedis.expire(key, seconds);
            closeJedis(jedis);
            return result;
        }

        /**
         * ���ù���ʱ�䣬Ĭ��ֵΪ60000seconds
         *
         * @param key
         */
        public long expire(String key) {
            return expire(key, EXPIRE);
        }

        /**
         * ����key�Ĺ���ʱ��,���Ǿ���Ԫ�����������α�׼ʱ�� 1970 �� 1 �� 1 �յ� 00:00:00���������������ƫ������
         *
         * @param key
         * @param timestamp ��
         * @return Ӱ��ļ�¼��
         */
        public long expireAt(String key, long timestamp) {
            Jedis jedis = getJedis();
            long count = jedis.expireAt(key, timestamp);
            closeJedis(jedis);
            return count;
        }

        /**
         * ��ѯkey�Ĺ���ʱ��
         *
         * @param key
         * @return ����Ϊ��λ��ʱ���ʾ
         */
        public long ttl(String key) {
            //ShardedJedis sjedis = getShardedJedis();
            Jedis sjedis = getJedis();
            long len = sjedis.ttl(key);
            closeJedis(sjedis);
            return len;
        }

        /**
         * ȡ����key����ʱ�������
         *
         * @param key
         * @return Ӱ��ļ�¼��
         */
        public long persist(String key) {
            Jedis jedis = getJedis();
            long count = jedis.persist(key);
            closeJedis(jedis);
            return count;
        }

        /**
         * �������key
         *
         * @return
         */
        public String flushAll() {
            Jedis jedis = getJedis();
            String stata = jedis.flushAll();
            closeJedis(jedis);
            return stata;
        }

        /**
         * �ж�key�Ƿ����
         *
         * @param key
         * @return boolean
         */
        public boolean exists(String key) {
            Jedis sjedis = getJedis();
            boolean exis = sjedis.exists(key);
            closeJedis(sjedis);
            return exis;
        }

        /**
         * ����key
         */
        public String rename(String oldKey, String newKey) {
            return rename(SafeEncoder.encode(oldKey),
                    SafeEncoder.encode(newKey));
        }

        /**
         * ����key,������key������ʱ��ִ��
         *
         * @param oldKey
         * @param newKey
         * @return ״̬��
         */
        public long renamenx(String oldKey, String newKey) {
            Jedis jedis = getJedis();
            long status = jedis.renamenx(oldKey, newKey);
            closeJedis(jedis);
            return status;
        }

        /**
         * ����key
         */
        public String rename(byte[] oldKey, byte[] newKey) {
            Jedis jedis = getJedis();
            String status = jedis.rename(oldKey, newKey);
            closeJedis(jedis);
            return status;
        }


        /**
         * ɾ��keys��Ӧ�ļ�¼,�����Ƕ��key
         *
         * @param keys
         * @return ɾ���ļ�¼��
         */
        public long del(String... keys) {
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            closeJedis(jedis);
            return count;
        }

        /**
         * ɾ��keys��Ӧ�ļ�¼,�����Ƕ��key
         *
         * @param keys
         * @return ɾ���ļ�¼��
         */
        public long del(byte[]... keys) {
            Jedis jedis = getJedis();
            long count = jedis.del(keys);
            closeJedis(jedis);
            return count;
        }


        /**
         * ��List,Set,SortSet��������,����������ݽϴ�Ӧ����ʹ���������
         *
         * @param key
         * @return List<String> ���ϵ�ȫ����¼
         **/
        public List<String> sort(String key) {
            Jedis sjedis = getJedis();
            List<String> list = sjedis.sort(key);
            closeJedis(sjedis);
            return list;
        }

        /**
         * ��List,Set,SortSet���������limit
         *
         * @param key
         * @param parame �����������ͻ�limit����ֹλ��.
         * @return List<String> ȫ���򲿷ּ�¼
         **/
        public List<String> sort(String key, SortingParams parame) {
            Jedis jedis = getJedis();
            List<String> list = jedis.sort(key, parame);
            closeJedis(jedis);
            return list;
        }

        /**
         * ����ָ��key�洢������
         *
         * @param key
         * @return String string|list|set|zset|hash
         **/
        public String type(String key) {
            Jedis sjedis = getJedis();
            String type = sjedis.type(key);
            closeJedis(sjedis);
            return type;
        }

        /**
         * ��������ƥ�������ģʽ�ļ�
         *
         * @param pattern �ı��ʽ,*��ʾ���������ʾһ��
         */
        public Set<String> keys(String pattern) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            closeJedis(jedis);
            return set;
        }
    }

    //*******************************************Sets*******************************************//  
    public class Sets {

        /**
         * ��Set���һ����¼�����member�Ѵ��ڷ���0,���򷵻�1
         *
         * @param key
         * @param member
         * @return ������, 0��1
         */
        public long sadd(String key, String member) {
            Jedis jedis = getJedis();
            long s = jedis.sadd(key, member);
            closeJedis(jedis);
            return s;
        }

        public long sadd(byte[] key, byte[] member) {
            Jedis jedis = getJedis();
            long s = jedis.sadd(key, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��ȡ����key��Ԫ�ظ���
         *
         * @param key
         * @return Ԫ�ظ���
         */
        public long scard(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            long len = sjedis.scard(key);
            closeJedis(sjedis);
            return len;
        }

        /**
         * ���شӵ�һ������еĸ�������֮��Ĳ���ĳ�Ա
         *
         * @param keys
         * @return ����ĳ�Ա����
         */
        public Set<String> sdiff(String... keys) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.sdiff(keys);
            closeJedis(jedis);
            return set;
        }

        /**
         * ����������sdiff,�����صĲ��ǽ����,���ǽ�������洢���µļ����У����Ŀ���Ѵ��ڣ��򸲸ǡ�
         *
         * @param newKey �½������key
         * @param keys   �Ƚϵļ���
         * @return �¼����еļ�¼��
         **/
        public long sdiffstore(String newKey, String... keys) {
            Jedis jedis = getJedis();
            long s = jedis.sdiffstore(newKey, keys);
            closeJedis(jedis);
            return s;
        }

        /**
         * ���ظ������Ͻ����ĳ�Ա,�������һ������Ϊ�����ڻ�Ϊ�գ��򷵻ؿ�Set
         *
         * @param keys
         * @return ������Ա�ļ���
         **/
        public Set<String> sinter(String... keys) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.sinter(keys);
            closeJedis(jedis);
            return set;
        }

        /**
         * ����������sinter,�����صĲ��ǽ����,���ǽ�������洢���µļ����У����Ŀ���Ѵ��ڣ��򸲸ǡ�
         *
         * @param newKey �½������key
         * @param keys   �Ƚϵļ���
         * @return �¼����еļ�¼��
         **/
        public long sinterstore(String newKey, String... keys) {
            Jedis jedis = getJedis();
            long s = jedis.sinterstore(newKey, keys);
            closeJedis(jedis);
            return s;
        }

        /**
         * ȷ��һ��������ֵ�Ƿ����
         *
         * @param key
         * @param member Ҫ�жϵ�ֵ
         * @return ���ڷ���1�������ڷ���0
         **/
        public boolean sismember(String key, String member) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            boolean s = sjedis.sismember(key, member);
            closeJedis(sjedis);
            return s;
        }

        /**
         * ���ؼ����е����г�Ա
         *
         * @param key
         * @return ��Ա����
         */
        public Set<String> smembers(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<String> set = sjedis.smembers(key);
            closeJedis(sjedis);
            return set;
        }

        public Set<byte[]> smembers(byte[] key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<byte[]> set = sjedis.smembers(key);
            closeJedis(sjedis);
            return set;
        }

        /**
         * ����Ա��Դ�����Ƴ�����Ŀ�꼯�� <br/>
         * ���Դ���ϲ����ڻ򲻰���ָ����Ա���������κβ���������0<br/>
         * ����ó�Ա��Դ������ɾ��������ӵ�Ŀ�꼯�ϣ����Ŀ�꼯���г�Ա�Ѵ��ڣ���ֻ��Դ���Ͻ���ɾ��
         *
         * @param srckey Դ����
         * @param dstkey Ŀ�꼯��
         * @param member Դ�����еĳ�Ա
         * @return ״̬�룬1�ɹ���0ʧ��
         */
        public long smove(String srckey, String dstkey, String member) {
            Jedis jedis = getJedis();
            long s = jedis.smove(srckey, dstkey, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * �Ӽ�����ɾ����Ա
         *
         * @param key
         * @return ��ɾ���ĳ�Ա
         */
        public String spop(String key) {
            Jedis jedis = getJedis();
            String s = jedis.spop(key);
            closeJedis(jedis);
            return s;
        }

        /**
         * �Ӽ�����ɾ��ָ����Ա
         *
         * @param key
         * @param member Ҫɾ���ĳ�Ա
         * @return ״̬�룬�ɹ�����1����Ա�����ڷ���0
         */
        public long srem(String key, String member) {
            Jedis jedis = getJedis();
            long s = jedis.srem(key, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * �ϲ�������ϲ����غϲ���Ľ�����ϲ���Ľ�����ϲ�������<br/>
         *
         * @param keys
         * @return �ϲ���Ľ������
         */
        public Set<String> sunion(String... keys) {
            Jedis jedis = getJedis();
            Set<String> set = jedis.sunion(keys);
            closeJedis(jedis);
            return set;
        }

        /**
         * �ϲ�������ϲ����ϲ���Ľ����������ָ�����¼����У�����¼����Ѿ������򸲸�
         *
         * @param newKey �¼��ϵ�key
         * @param keys   Ҫ�ϲ��ļ���
         **/
        public long sunionstore(String newKey, String... keys) {
            Jedis jedis = getJedis();
            long s = jedis.sunionstore(newKey, keys);
            closeJedis(jedis);
            return s;
        }
    }

    //*******************************************SortSet*******************************************//  
    public class SortSet {

        /**
         * �򼯺�������һ����¼,������ֵ�Ѵ��ڣ����ֵ��Ӧ��Ȩ�ؽ�����Ϊ�µ�Ȩ��
         *
         * @param key
         * @param score  Ȩ��
         * @param member Ҫ�����ֵ��
         * @return ״̬�� 1�ɹ���0�Ѵ���member��ֵ
         */
        public long zadd(String key, double score, String member) {
            Jedis jedis = getJedis();
            long s = jedis.zadd(key, score, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��ȡ������Ԫ�ص�����
         *
         * @param key
         * @return �������0�򼯺ϲ�����
         */
        public long zcard(String key) {
            Jedis sjedis = getJedis();
            long len = sjedis.zcard(key);
            closeJedis(sjedis);
            return len;
        }

        /**
         * ��ȡָ��Ȩ�������ڼ��ϵ�����
         *
         * @param key
         * @param min ��С����λ��
         * @param max �������λ��
         */
        public long zcount(String key, double min, double max) {
            Jedis sjedis = getJedis();
            long len = sjedis.zcount(key, min, max);
            closeJedis(sjedis);
            return len;
        }

        /**
         * ���set�ĳ���
         *
         * @param key
         * @return
         */
        public long zlength(String key) {
            long len = 0;
            Set<String> set = zrange(key, 0, -1);
            len = set.size();
            return len;
        }

        /**
         * Ȩ�����Ӹ���ֵ�����������member�Ѵ���
         *
         * @param key
         * @param score  Ҫ����Ȩ��
         * @param member Ҫ�����ֵ
         * @return �����Ȩ��
         */
        public double zincrby(String key, double score, String member) {
            Jedis jedis = getJedis();
            double s = jedis.zincrby(key, score, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * ����ָ��λ�õļ���Ԫ��,0Ϊ��һ��Ԫ�أ�-1Ϊ���һ��Ԫ��
         *
         * @param key
         * @param start ��ʼλ��(����)
         * @param end   ����λ��(����)
         * @return Set<String>
         */
        public Set<String> zrange(String key, int start, int end) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<String> set = sjedis.zrange(key, start, end);
            closeJedis(sjedis);
            return set;
        }

        /**
         * ����ָ��Ȩ�������Ԫ�ؼ���
         *
         * @param key
         * @param min ����Ȩ��
         * @param max ����Ȩ��
         * @return Set<String>
         */
        public Set<String> zrangeByScore(String key, double min, double max) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<String> set = sjedis.zrangeByScore(key, min, max);
            closeJedis(sjedis);
            return set;
        }

        /**
         * ��ȡָ��ֵ�ڼ����е�λ�ã���������ӵ͵���
         *
         * @param key
         * @param member
         * @return long λ��
         */
        public long zrank(String key, String member) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            long index = sjedis.zrank(key, member);
            closeJedis(sjedis);
            return index;
        }

        /**
         * ��ȡָ��ֵ�ڼ����е�λ�ã���������Ӹߵ���
         *
         * @param key
         * @param member
         * @return long λ��
         */
        public long zrevrank(String key, String member) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            long index = sjedis.zrevrank(key, member);
            closeJedis(sjedis);
            return index;
        }

        /**
         * �Ӽ�����ɾ����Ա
         *
         * @param key
         * @param member
         * @return ����1�ɹ�
         */
        public long zrem(String key, String member) {
            Jedis jedis = getJedis();
            long s = jedis.zrem(key, member);
            closeJedis(jedis);
            return s;
        }

        /**
         * ɾ��
         *
         * @param key
         * @return
         */
        public long zrem(String key) {
            Jedis jedis = getJedis();
            long s = jedis.del(key);
            closeJedis(jedis);
            return s;
        }

        /**
         * ɾ������λ�������Ԫ��
         *
         * @param key
         * @param start ��ʼ���䣬��0��ʼ(����)
         * @param end   ��������,-1Ϊ���һ��Ԫ��(����)
         * @return ɾ��������
         */
        public long zremrangeByRank(String key, int start, int end) {
            Jedis jedis = getJedis();
            long s = jedis.zremrangeByRank(key, start, end);
            closeJedis(jedis);
            return s;
        }

        /**
         * ɾ������Ȩ�������Ԫ��
         *
         * @param key
         * @param min ����Ȩ��(����)
         * @param max ����Ȩ��(����)
         * @return ɾ��������
         */
        public long zremrangeByScore(String key, double min, double max) {
            Jedis jedis = getJedis();
            long s = jedis.zremrangeByScore(key, min, max);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��ȡ���������Ԫ�أ�ԭʼ����Ȩ���ɸߵ�������
         *
         * @param key
         * @param start
         * @param end
         * @return Set<String>
         */
        public Set<String> zrevrange(String key, int start, int end) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<String> set = sjedis.zrevrange(key, start, end);
            closeJedis(sjedis);
            return set;
        }

        /**
         * ��ȡ����ֵ�ڼ����е�Ȩ��
         *
         * @param key
         * @param memebr
         * @return double Ȩ��
         */
        public double zscore(String key, String memebr) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Double score = sjedis.zscore(key, memebr);
            closeJedis(sjedis);
            if (score != null)
                return score;
            return 0;
        }
    }

    //*******************************************Hash*******************************************//  
    public class Hash {

        /**
         * ��hash��ɾ��ָ���Ĵ洢
         *
         * @param key
         * @param fieid �洢������
         * @return ״̬�룬1�ɹ���0ʧ��
         */
        public long hdel(String key, String fieid) {
            Jedis jedis = getJedis();
            long s = jedis.hdel(key, fieid);
            closeJedis(jedis);
            return s;
        }

        public long hdel(String key) {
            Jedis jedis = getJedis();
            long s = jedis.del(key);
            closeJedis(jedis);
            return s;
        }

        /**
         * ����hash��ָ���Ĵ洢�Ƿ����
         *
         * @param key
         * @param fieid �洢������
         * @return 1���ڣ�0������
         */
        public boolean hexists(String key, String fieid) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            boolean s = sjedis.hexists(key, fieid);
            closeJedis(sjedis);
            return s;
        }

        /**
         * ����hash��ָ���洢λ�õ�ֵ
         *
         * @param key
         * @param fieid �洢������
         * @return �洢��Ӧ��ֵ
         */
        public String hget(String key, String fieid) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            String s = sjedis.hget(key, fieid);
            closeJedis(sjedis);
            return s;
        }

        public byte[] hget(byte[] key, byte[] fieid) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            byte[] s = sjedis.hget(key, fieid);
            closeJedis(sjedis);
            return s;
        }

        /**
         * ��Map����ʽ����hash�еĴ洢��ֵ
         *
         * @param key
         * @return Map<Strinig,String>
         */
        public Map<String, String> hgetAll(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Map<String, String> map = sjedis.hgetAll(key);
            closeJedis(sjedis);
            return map;
        }

        /**
         * ���һ����Ӧ��ϵ
         *
         * @param key
         * @param fieid
         * @param value
         * @return ״̬�� 1�ɹ���0ʧ�ܣ�fieid�Ѵ��ڽ����£�Ҳ����0
         **/
        public long hset(String key, String fieid, String value) {
            Jedis jedis = getJedis();
            long s = jedis.hset(key, fieid, value);
            closeJedis(jedis);
            return s;
        }

        public long hset(String key, String fieid, byte[] value) {
            Jedis jedis = getJedis();
            long s = jedis.hset(key.getBytes(), fieid.getBytes(), value);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��Ӷ�Ӧ��ϵ��ֻ����fieid������ʱ��ִ��
         *
         * @param key
         * @param fieid
         * @param value
         * @return ״̬�� 1�ɹ���0ʧ��fieid�Ѵ�
         **/
        public long hsetnx(String key, String fieid, String value) {
            Jedis jedis = getJedis();
            long s = jedis.hsetnx(key, fieid, value);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��ȡhash��value�ļ���
         *
         * @param key
         * @return List<String>
         */
        public List<String> hvals(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            List<String> list = sjedis.hvals(key);
            closeJedis(sjedis);
            return list;
        }

        /**
         * ��ָ���Ĵ洢λ�ü���ָ�������֣��洢λ�õ�ֵ�����תΪ��������
         *
         * @param key
         * @param fieid �洢λ��
         * @param value Ҫ���ӵ�ֵ,�����Ǹ���
         * @return ����ָ�����ֺ󣬴洢λ�õ�ֵ
         */
        public long hincrby(String key, String fieid, long value) {
            Jedis jedis = getJedis();
            long s = jedis.hincrBy(key, fieid, value);
            closeJedis(jedis);
            return s;
        }

        /**
         * ����ָ��hash�е����д洢����,����Map�е�keySet����
         *
         * @param key
         * @return Set<String> �洢���Ƶļ���
         */
        public Set<String> hkeys(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            Set<String> set = sjedis.hkeys(key);
            closeJedis(sjedis);
            return set;
        }

        /**
         * ��ȡhash�д洢�ĸ���������Map��size����
         *
         * @param key
         * @return long �洢�ĸ���
         */
        public long hlen(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            long len = sjedis.hlen(key);
            closeJedis(sjedis);
            return len;
        }

        /**
         * ���ݶ��key����ȡ��Ӧ��value������List,���ָ����key������,List��Ӧλ��Ϊnull
         *
         * @param key
         * @param fieids �洢λ��
         * @return List<String>
         */
        public List<String> hmget(String key, String... fieids) {
            Jedis sjedis = getJedis();
            List<String> list = sjedis.hmget(key, fieids);
            closeJedis(sjedis);
            return list;
        }

        public List<byte[]> hmget(byte[] key, byte[]... fieids) {
            Jedis sjedis = getJedis();
            List<byte[]> list = sjedis.hmget(key, fieids);
            closeJedis(sjedis);
            return list;
        }

        /**
         * ��Ӷ�Ӧ��ϵ�������Ӧ��ϵ�Ѵ��ڣ��򸲸�
         *
         * @param key
         * @param map ��Ӧ��ϵ
         * @return ״̬���ɹ�����OK
         */
        public String hmset(String key, Map<String, String> map) {
            Jedis jedis = getJedis();
            String s = jedis.hmset(key, map);
            closeJedis(jedis);
            return s;
        }

        /**
         * ��Ӷ�Ӧ��ϵ�������Ӧ��ϵ�Ѵ��ڣ��򸲸�
         *
         * @param key
         * @param map ��Ӧ��ϵ
         * @return ״̬���ɹ�����OK
         */
        public String hmset(byte[] key, Map<byte[], byte[]> map) {
            Jedis jedis = getJedis();
            String s = jedis.hmset(key, map);
            closeJedis(jedis);
            return s;
        }

    }


    //*******************************************Strings*******************************************//  
    public class Strings {
        /**
         * ����key��ȡ��¼
         *
         * @param key
         * @return ֵ
         */
        public String get(String key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            String value = sjedis.get(key);
            closeJedis(sjedis);
            return value;
        }

        /**
         * ����key��ȡ��¼
         *
         * @param key
         * @return ֵ
         */
        public byte[] get(byte[] key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            byte[] value = sjedis.get(key);
            closeJedis(sjedis);
            return value;
        }

        /**
         * ����й���ʱ��ļ�¼
         *
         * @param key
         * @param seconds ����ʱ�䣬����Ϊ��λ
         * @param value
         * @return String ����״̬
         */
        public String setEx(String key, int seconds, String value) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            closeJedis(jedis);
            return str;
        }

        /**
         * ����й���ʱ��ļ�¼
         *
         * @param key
         * @param seconds ����ʱ�䣬����Ϊ��λ
         * @param value
         * @return String ����״̬
         */
        public String setEx(byte[] key, int seconds, byte[] value) {
            Jedis jedis = getJedis();
            String str = jedis.setex(key, seconds, value);
            closeJedis(jedis);
            return str;
        }

        /**
         * ���һ����¼������������key������ʱ�Ų���
         *
         * @param key
         * @param value
         * @return long ״̬�룬1����ɹ���key�����ڣ�0δ���룬key����
         */
        public long setnx(String key, String value) {
            Jedis jedis = getJedis();
            long str = jedis.setnx(key, value);
            closeJedis(jedis);
            return str;
        }

        /**
         * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value
         *
         * @param key
         * @param value
         * @return ״̬��
         */
        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value
         *
         * @param key
         * @param value
         * @return ״̬��
         */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value
         *
         * @param key
         * @param value
         * @return ״̬��
         */
        public String set(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            String status = jedis.set(key, value);
           System.out.println("status:"+status);
            closeJedis(jedis);
            return status;
        }

        /**
         * ��ָ��λ�ÿ�ʼ�������ݣ���������ݻḲ��ָ��λ���Ժ������<br/>
         * ��:String str1="123456789";<br/>
         * ��str1������setRange(key,4,0000)��str1="123400009";
         *
         * @param key
         * @param offset
         * @param value
         * @return long value�ĳ���
         */
        public long setRange(String key, long offset, String value) {
            Jedis jedis = getJedis();
            long len = jedis.setrange(key, offset, value);
            closeJedis(jedis);
            return len;
        }

        /**
         * ��ָ����key��׷��value
         *
         * @param key
         * @param value
         * @return long ׷�Ӻ�value�ĳ���
         **/
        public long append(String key, String value) {
            Jedis jedis = getJedis();
            long len = jedis.append(key, value);
            closeJedis(jedis);
            return len;
        }

        /**
         * ��key��Ӧ��value��ȥָ����ֵ��ֻ��value����תΪ����ʱ�÷����ſ���
         *
         * @param key
         * @param number Ҫ��ȥ��ֵ
         * @return long ��ָ��ֵ���ֵ
         */
        public long decrBy(String key, long number) {
            Jedis jedis = getJedis();
            long len = jedis.decrBy(key, number);
            closeJedis(jedis);
            return len;
        }

        /**
         * <b>������Ϊ��ȡΨһid�ķ���</b><br/>
         * ��key��Ӧ��value����ָ����ֵ��ֻ��value����תΪ����ʱ�÷����ſ���
         *
         * @param key
         * @param number Ҫ��ȥ��ֵ
         * @return long ��Ӻ��ֵ
         */
        public long incrBy(String key, long number) {
            Jedis jedis = getJedis();
            long len = jedis.incrBy(key, number);
            closeJedis(jedis);
            return len;
        }

        /**
         * ��ָ��key��Ӧ��value���н�ȡ
         *
         * @param key
         * @param startOffset ��ʼλ��(����)
         * @param endOffset   ����λ��(����)
         * @return String ��ȡ��ֵ
         */
        public String getrange(String key, long startOffset, long endOffset) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            String value = sjedis.getrange(key, startOffset, endOffset);
            closeJedis(sjedis);
            return value;
        }

        /**
         * ��ȡ������ָ��key��Ӧ��value<br/>
         * ���key���ڷ���֮ǰ��value,���򷵻�null
         *
         * @param key
         * @param value
         * @return String ԭʼvalue��null
         */
        public String getSet(String key, String value) {
            Jedis jedis = getJedis();
            String str = jedis.getSet(key, value);
            closeJedis(jedis);
            return str;
        }

        /**
         * ������ȡ��¼,���ָ����key�����ڷ���List�Ķ�Ӧλ�ý���null
         *
         * @param keys
         * @return List<String> ֵ�ü���
         */
        public List<String> mget(String... keys) {
            Jedis jedis = getJedis();
            List<String> str = jedis.mget(keys);
            closeJedis(jedis);
            return str;
        }

        /**
         * �����洢��¼
         *
         * @param keysvalues ��:keysvalues="key1","value1","key2","value2";
         * @return String ״̬��
         */
        public String mset(String... keysvalues) {
            Jedis jedis = getJedis();
            String str = jedis.mset(keysvalues);
            closeJedis(jedis);
            return str;
        }

        /**
         * ��ȡkey��Ӧ��ֵ�ĳ���
         *
         * @param key
         * @return valueֵ�ó���
         */
        public long strlen(String key) {
            Jedis jedis = getJedis();
            long len = jedis.strlen(key);
            closeJedis(jedis);
            return len;
        }
    }


    //*******************************************Lists*******************************************//  
    public class Lists {
        /**
         * List����
         *
         * @param key
         * @return ����
         */
        public long llen(String key) {
            return llen(SafeEncoder.encode(key));
        }

        /**
         * List����
         *
         * @param key
         * @return ����
         */
        public long llen(byte[] key) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            long count = sjedis.llen(key);
            closeJedis(sjedis);
            return count;
        }

        /**
         * ���ǲ���,������List��ָ��λ�õ�ֵ
         *
         * @param key
         * @param index λ��
         * @param value ֵ
         * @return ״̬��
         */
        public String lset(byte[] key, int index, byte[] value) {
            Jedis jedis = getJedis();
            String status = jedis.lset(key, index, value);
            closeJedis(jedis);
            return status;
        }

        /**
         * ���ǲ���,������List��ָ��λ�õ�ֵ
         *
         * @param
         * @param index λ��
         * @param value ֵ
         * @return ״̬��
         */
        public String lset(String key, int index, String value) {
            return lset(SafeEncoder.encode(key), index,
                    SafeEncoder.encode(value));
        }

        /**
         * ��ȡList��ָ��λ�õ�ֵ
         *
         * @param key
         * @param index λ��
         * @return ֵ
         **/
        public String lindex(String key, int index) {
            return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
        }

        /**
         * ��ȡList��ָ��λ�õ�ֵ
         *
         * @param key
         * @param index λ��
         * @return ֵ
         **/
        public byte[] lindex(byte[] key, int index) {
            Jedis sjedis = getJedis();
            byte[] value = sjedis.lindex(key, index);
            closeJedis(sjedis);
            return value;
        }

        /**
         * ��List�еĵ�һ����¼�Ƴ�List
         *
         * @param key
         * @return �Ƴ��ļ�¼
         */
        public String lpop(String key) {
            return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
        }

        /**
         * ��List�еĵ�һ����¼�Ƴ�List
         *
         * @param key
         * @return �Ƴ��ļ�¼
         */
        public byte[] lpop(byte[] key) {
            Jedis jedis = getJedis();
            byte[] value = jedis.lpop(key);
            closeJedis(jedis);
            return value;
        }

        /**
         * ��List������һ����¼�Ƴ�List
         *
         * @param key
         * @return �Ƴ��ļ�¼
         */
        public String rpop(String key) {
            Jedis jedis = getJedis();
            String value = jedis.rpop(key);
            closeJedis(jedis);
            return value;
        }

        /**
         * ��Listβ��׷�Ӽ�¼
         *
         * @param key
         * @param value
         * @return ��¼����
         */
        public long lpush(String key, String value) {
            return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * ��Listͷ��׷�Ӽ�¼
         *
         * @param key
         * @param value
         * @return ��¼����
         */
        public long rpush(String key, String value) {
            Jedis jedis = getJedis();
            long count = jedis.rpush(key, value);
            closeJedis(jedis);
            return count;
        }

        /**
         * ��Listͷ��׷�Ӽ�¼
         *
         * @param key
         * @param value
         * @return ��¼����
         */
        public long rpush(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            long count = jedis.rpush(key, value);
            closeJedis(jedis);
            return count;
        }

        /**
         * ��List��׷�Ӽ�¼
         *
         * @param key
         * @param value
         * @return ��¼����
         */
        public long lpush(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            long count = jedis.lpush(key, value);
            closeJedis(jedis);
            return count;
        }

        /**
         * ��ȡָ����Χ�ļ�¼��������Ϊ��ҳʹ��
         *
         * @param key
         * @param start
         * @param end
         * @return List
         */
        public List<String> lrange(String key, long start, long end) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            List<String> list = sjedis.lrange(key, start, end);
            closeJedis(sjedis);
            return list;
        }

        /**
         * ��ȡָ����Χ�ļ�¼��������Ϊ��ҳʹ��
         *
         * @param key
         * @param start
         * @param end   ���Ϊ��������β����ʼ����
         * @return List
         */
        public List<byte[]> lrange(byte[] key, int start, int end) {
            //ShardedJedis sjedis = getShardedJedis();  
            Jedis sjedis = getJedis();
            List<byte[]> list = sjedis.lrange(key, start, end);
            closeJedis(sjedis);
            return list;
        }

        /**
         * ɾ��List��c����¼����ɾ���ļ�¼ֵΪvalue
         *
         * @param key
         * @param c     Ҫɾ�������������Ϊ�������List��β����鲢ɾ�����ϵļ�¼
         * @param value Ҫƥ���ֵ
         * @return ɾ�����List�еļ�¼��
         */
        public long lrem(byte[] key, int c, byte[] value) {
            Jedis jedis = getJedis();
            long count = jedis.lrem(key, c, value);
            closeJedis(jedis);
            return count;
        }

        /**
         * ɾ��List��c����¼����ɾ���ļ�¼ֵΪvalue
         *
         * @param key
         * @param c     Ҫɾ�������������Ϊ�������List��β����鲢ɾ�����ϵļ�¼
         * @param value Ҫƥ���ֵ
         * @return ɾ�����List�еļ�¼��
         */
        public long lrem(String key, int c, String value) {
            return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
        }

        /**
         * ����ɾ���ɣ�ֻ����start��end֮��ļ�¼
         *
         * @param key
         * @param start ��¼�Ŀ�ʼλ��(0��ʾ��һ����¼)
         * @param end   ��¼�Ľ���λ�ã����Ϊ-1���ʾ���һ����-2��-3�Դ����ƣ�
         * @return ִ��״̬��
         */
        public String ltrim(byte[] key, int start, int end) {
            Jedis jedis = getJedis();
            String str = jedis.ltrim(key, start, end);
            closeJedis(jedis);
            return str;
        }

        /**
         * ����ɾ���ɣ�ֻ����start��end֮��ļ�¼
         *
         * @param key
         * @param start ��¼�Ŀ�ʼλ��(0��ʾ��һ����¼)
         * @param end   ��¼�Ľ���λ�ã����Ϊ-1���ʾ���һ����-2��-3�Դ����ƣ�
         * @return ִ��״̬��
         */
        public String ltrim(String key, int start, int end) {
            return ltrim(SafeEncoder.encode(key), start, end);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("1", 1);
        map.put("2", "sfasdfa");
        map.put("3", "sfawrwere3fa");

        String werwe = RedisUtil.getInstance().strings().set("werwe", JSON.toJSONString(map));
        System.out.println(werwe);
        String s = RedisUtil.getInstance().strings().get("werwe");
        System.out.println(s);
    }    
}  
