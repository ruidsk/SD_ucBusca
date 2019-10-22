import java.net.*;int port=5000;group="225.4.5.6";

// Create the socket and bind it to port 'port'. MulticastSocket s = new
MulticastSocket(port);

// join the multicast group
s.joinGroup(InetAddress.getByName(group));

// Now the socket is set up and we are ready to receive packets
byte buf[]=byte[1024];DatagramPacket pack=new DatagramPacket(buf,buf.length);s.receive(pack);

// Do something useful with the data we just received,
System.out.println("Received data from: "+pack.getAddress().toString()+":"+pack.getPort()+" with length: "+pack.getLength());System.out.write(pack.getData(),0,pack.getLength());
// And when we have finished receiving data leave the multicast group and close
// the socket
s.leaveGroup(InetAddress.getByName(group);s.close();