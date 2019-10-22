//Multicast peer joins a group and sends and receives datagrams

MulticastSocket socket = null;
try {
InetAddress group = InetAddress.getByName(“225.0.0.0”); socket = new MulticastSocket(6789); socket.joinGroup(group);
socket.setTimeToLive(1);
byte[] buf = “Hello”.getBytes();
DatagramPacket mesgOut =
new DatagramPacket(buf, buf.length, group, port); socket.send(msgOut);
////
byte[] inBuf = new byte[8*1024]; DatagramPacket msgIn =
new DatagramPacket(inBuf, inBuf.length); socket.receive(msgIn);
System.out.println("Received:" + new String(inBuf, 0, msgIn.getLength()));
socket.leaveGroup(group);
} catch (Exception e) { e.printStackTrace(); }