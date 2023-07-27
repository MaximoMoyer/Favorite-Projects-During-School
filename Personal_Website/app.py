from flask import Flask, redirect, url_for, request, session, render_template
import Profile_Generation as PG
import uuid
import os
import shutil

app = Flask(__name__)

#defining function to run on shutdown
		
@app.route('/')
def homepage():
	session['uid'] = uuid.uuid4()
	return render_template('index.html')

@app.route('/loading', methods=['POST'])
def loading():
	if request.method == 'POST':
		prof_desc = request.form['nm']
		session['prof_desc'] = prof_desc
		return render_template('loading.html')

@app.route('/create_profile')
def create_profile():
	prof_desc = session['prof_desc']
	profile_generator = PG.Profile_Generator()
	similair_artist = profile_generator.get_similair_artist(prof_desc)
	session['similair_artist'] = similair_artist
	image_status = profile_generator.get_image(prof_desc, similair_artist, session['uid'])
	if image_status  != 'success':
		profile_generator.get_image("a toucan wearing a hat", "vermeer", session['uid'])
	session['image_status'] = image_status
	return 'image creation done'

@app.route('/landing')
def landing():
	image_status = session['image_status']
	prof_desc = session['prof_desc']
	similair_artist = session['similair_artist']
	return render_template('landing.html', sentence = prof_desc, similair_artist = similair_artist, image_status = image_status, sess_id = session['uid'])

@app.route('/about_me')
def about_me():
	return render_template('aboutme.html', sess_id = session['uid'])

@app.route('/reading')
def reading():
	return render_template('reading.html', sess_id = session['uid'])

@app.route('/chore_bot')
def chore_bot():
	return render_template('chorebot.html', sess_id = session['uid']) 

@app.route('/delete')
def delete():
	if os.path.exists(f"./static/{session['uid']}"):
		shutil.rmtree(f"./static/{session['uid']}")
	return 'exited'


if __name__ == '__main__':
	app.secret_key = 'mysecretkey'
	app.run(debug=False)