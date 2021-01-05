require 'socket'

SOCK = TCPSocket.new '192.168.1.2', 10000
REAL_EVAL = Kernel.method :eval
puts SOCK.readline

#def eval a, b, c, d
#       SOCK.write(a)
#       puts SOCK.readline
#       REAL_EVAL.call a, b, c, d
#       ""
#end