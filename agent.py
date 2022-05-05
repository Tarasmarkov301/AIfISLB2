mass = []
lis = []
history = []
px = 0
py = 0
fx = 100
fy = 100

def action(x, y):
    i = -1
    while i <= 1:
        j = -1
        while j <= 1:
            if x+i < 300 and x+i >= 0 and y+j < 300 and y+j >= 0 and not(i == 0 and j == 0):
                if (abs(mass[300*(x+i)+y+j]-mass[300*x+y])) <= 2:
                    lis.append([i, j])
            j = j+1
        i = i+1


def prices(move, xx, yy, x, y):
    i = move[0]
    j = move[1]
    price = 0
    price = abs(mass[300*(x+i)+y+j]-mass[300*x+y])
    price = price+history.count(str(x+i)+" "+str(y+j))*6
    if x > xx and i < 0:
        price=price-1
    if y < yy and j > 0:
        price=price-1
    if x < xx and i > 0:
        price=price-1
    if y > yy and j < 0:
        price=price-1
    if x < xx and i < 0:
        price=price+1
    if x > xx and i > 0:
        price=price+1
    if y < yy and j < 0:
        price=price+1
    if y > yy and j > 0:
        price=price+1
    return price


def findBest(lists, x, y, xx, yy):
    price1 = 100000
    xl = 0
    yl = 0
    while(len(lists) != 0):
        p = lists.pop()
        pr = prices(p, xx, yy, x, y)
        if pr <= price1:
            xl = p[0]
            yl = p[1]
            price1 = pr
    return[xl, yl]

def writeInFile():
    f = open('text2.txt','w')
    while(len(history) != 0):
        f.write(history.pop()+" ")
    f.close()

j=0
f = open('text.txt', 'r')
number = f.readline().split(' ')
px = int(number[0])
py = int(number[1])
number = f.readline().split(' ')
fx = int(number[0])
fy = int(number[1])
while j < 300:
    number = f.readline().split(' ')
    i = 0
    while i < 300:
        mass.append(int(number[i]))
        i = i + 1
    j = j + 1
f.close()
while px != fx or py != fy:
    print(px, py)
    action(px, py)
    c = findBest(lis, px, py, fx, fy)
    px = px+c[0]
    py = py+c[1]
    history.append(str(px)+" "+str(py))

writeInFile()