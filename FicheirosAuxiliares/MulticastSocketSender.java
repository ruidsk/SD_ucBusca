import java.net.*;

int port=5000;String group="225.4.5.6";int ttl=1;

// Create the socket but we don't bind it as we are only going to send data
MulticastSocket s=new MulticastSocket();
// We don't have to join the multicast group if we are only sending data

// Fill the buffer with some data
byte buf[]=byte[10];for(int i=0;i<buf.length;i++)buf[i]=(byte)i;
// Create a DatagramPacket
DatagramPacket pack=new DatagramPacket(buf,buf.length,InetAddress.getByName(group),port);
// Do a send. Note that send takes a byte for the ttl and not an int.
s.send(pack,(byte)ttl);
// And when we have finished sending data close the socket
s.close();