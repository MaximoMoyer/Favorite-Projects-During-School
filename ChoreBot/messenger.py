

from twilio.rest import Client
from scraper import scraper
import schedule

 
account_sid = [fill me in] 
auth_token = [fill me in]
client = Client(account_sid, auth_token) 
 
myScraper = scraper('Apartments','Chores')

message_dict = myScraper.get_message_dict('F3','J4')
Numbers = {'Maximo':'+16107453636','Luke':'+12628539026','Christian':'+17035178183','Felipe':'+17033386052'}
body = ''

def send_message():
    for number in Numbers.keys():
        body = ''
        for keys in message_dict.keys():
            if keys == number:
                start = 0
                while not message_dict[keys][start].isspace():
                    start += 1

                body = '\nYour' + message_dict[keys][start:] + '\n' +'\nJust so you know what your dogs are cleaning:' + body
            else:
                body = body + ('\n'+'-' + message_dict[keys])
        message = client.messages.create(  
                                      messaging_service_sid='MG3d6eac9744d0122ffdd777cc422ec34f', 
                                      body= body,      
                                      to= Numbers[number]
                                  ) 

send_message()
schedule.every(168).hours.do(send_message)
 
while True:
    schedule.run_pending()

