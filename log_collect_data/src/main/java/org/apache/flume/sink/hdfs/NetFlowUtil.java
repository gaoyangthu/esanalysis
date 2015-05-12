package org.apache.flume.sink.hdfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author lky
 * netflow 工具类
 *
 */
public class NetFlowUtil {

	public static String long2IP(long longip) {
		// 将10进制整数形式转换成127.0.0.1形式的ip地址，在命令提示符下输入ping 3396362403l
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longip >>> 24));// 直接右移24位
		sb.append(".");
		sb.append(String.valueOf((longip & 0x00ffffff) >>> 16)); // 将高8位置0，然后右移16位
		sb.append(".");
		sb.append(String.valueOf((longip & 0x0000ffff) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longip & 0x000000ff));
		return sb.toString();
	}
	
	static public long to_number(byte[] p, int off, int len) {
		long ret = 0;
		int done = off + len;
		for (int i = off; i < done; i++)
			ret = ((ret << 8) & 0xffffffff) + (p[i] & 0xff);

		return ret;
    }
	
	
	static public byte[] v9Etl(String rip,byte[] buf){
		
		List<byte[]> lsb = new ArrayList<byte[]>();
		
		short version = (short) to_number(buf, 0, 2);
		long count = to_number(buf,  2, 2); 
		long unix_secs = to_number(buf,  8, 4);
		long Source_ID = to_number(buf,  16, 4);
		
		long flowsetId = to_number(buf, 20, 2);
        long flowsetLength = to_number(buf, 20 + 2, 2) ;
        
        int bsize = 0 ;
        //bufferWritter.write("flowsetId="+flowsetId+" flowsetLength="+flowsetLength+" length="+buf.length+"\n\n");
        if( flowsetId == 256l ){
        	
        	int one_flowset_size = (int) ((flowsetLength-4)/count) ;
        	for (int i = 0; i < count ; i++) {
            	StringBuffer sb = new StringBuffer();
            	
            	int ps = 24 + one_flowset_size*i;
            	
            	sb.append(unix_secs+"\t"+rip+"\t");
            	
            	String IPV4_SRC_ADDR = long2IP(to_number(buf,ps ,4));
            	sb.append(IPV4_SRC_ADDR).append("\t");
            	ps+=4;
            	
                String IPV4_DST_ADDR = long2IP(to_number(buf, ps,4));
                sb.append(IPV4_DST_ADDR).append("\t");
                ps+=4;
                
                long PROTOCOL = to_number(buf, ps,1);
                sb.append(PROTOCOL).append("\t");
                ps+=1;
                
                long SRC_TOS = to_number(buf, ps,1);
                sb.append(SRC_TOS).append("\t");
                ps+=1;
                
                
                long L4_SRC_PORT = to_number(buf, ps,2);
                sb.append(L4_SRC_PORT).append("\t");
                ps+=2;
                
                long L4_DST_PORT = to_number(buf, ps,2);
                sb.append(L4_DST_PORT).append("\t");
                ps+=2;
                
                
                long INPUT_SNMP = to_number(buf, ps,4);
                sb.append(INPUT_SNMP).append("\t");
                ps+=4;
                
                long OUTPUT_SNMP = to_number(buf, ps,4);
                sb.append(OUTPUT_SNMP).append("\t");
                ps+=4;
                
                long DIRECTION = to_number(buf, ps,1);
                sb.append(DIRECTION).append("\t");
                ps+=1;
                
                long SRC_AS = to_number(buf, ps,4);
                sb.append(SRC_AS).append("\t");
                ps+=4;
                
                
                long DST_AS = to_number(buf, ps,4);
                sb.append(DST_AS).append("\t");
                ps+=4;
                
                
                String IPV4_NEXT_HOP = long2IP(to_number(buf,ps ,4));
            	sb.append(IPV4_NEXT_HOP).append("\t");
            	ps+=4;
            	
            	
            	long TCP_FLAGS = to_number(buf, ps,1);
                sb.append(TCP_FLAGS).append("\t");
                ps+=1;
                
                
                long IN_BYTES = to_number(buf, ps,4);
                sb.append(IN_BYTES).append("\t");
                ps+=4;
            	
                long IN_PKTS = to_number(buf, ps,4);
                sb.append(IN_PKTS).append("\t");
                ps+=4;
                
                
                long FIRST_SWITCHED = to_number(buf, ps,4);
                sb.append(FIRST_SWITCHED).append("\t");
                ps+=4;
                
                long LAST_SWITCHED = to_number(buf, ps,4);
                if( (count-1)==i )
                	sb.append(LAST_SWITCHED);
                else
                	sb.append(LAST_SWITCHED).append("\n");
                ps+=4;
                
                byte[] bb = sb.toString().getBytes() ;
                bsize+=bb.length;
                lsb.add(bb);
    		}
        }
        
        
        byte[] bs = new byte[bsize];
        
        int index = 0;
        for(byte[] sb : lsb ){
        	for(int z = index,t=0 ;
        			z < index+sb.length ;
        			z++,t++){
        		bs[z] = sb[t];
        	}
        	index += sb.length ;
        }
        
		return bs;
        
	}
}
