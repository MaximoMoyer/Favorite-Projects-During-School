import Scraper as scraper
import Joint_Model as joint_model
import os
import shutil
import Image_Model as Image_Model


class Profile_Generator():
    def __init__(self):
        self.scraper = scraper.Scraper()
        self.artists = self.scraper.return_artists()
        self.joint_model = joint_model.Joint_Model()
        self.generate_embeddings()
        

    def generate_embeddings(self):
        #checks if each artist's embeddings have already been generated, if they have do nothing, if they haven't 
        #checks if photos are saved down for each artist

        if not os.path.exists('Artists/'):
            os.makedirs('Artists/')
        if not os.path.exists('Artists/Embeddings'):
            os.makedirs('Artists/Embeddings')
            
        #check if embeddings have been generated for each artist, if not generate them (and scrape any corresponding
        # images needed to generate the embeddings)
        embeddings = os.listdir('Artists/Embeddings')
        for artist in list(self.artists):
            if artist + '.pt' not in embeddings:
                if artist not in os.listdir('Artists/') or len(os.listdir('Artists/' + artist)) < 10:
                    file_path = os.path.join(os.getcwd(), 'Artists', artist)
                    if os.path.exists(file_path):
                        shutil.rmtree(file_path)
                    os.makedirs(file_path)
                    func_name = 'self.scraper.' + artist + '_Scraper'
                    eval(func_name + "(" + 'self.scraper.artists[artist]' + ")")
                self.joint_model.embedd_images([artist])
    
    def get_similair_artist(self, sentence):
        #check to see the artist that the sentence is most similar to
        most_similair = self.joint_model.check_similairity(sentence, self.artists)
        return most_similair

    def get_image(self,sentence,most_similair):
        prompt = sentence + 'painted by ' + most_similair
        image_outcome = Image_Model.produce_image(prompt)
        return image_outcome
        



def main():
    gen = Profile_Generator()
    sentence = "The quick brown fox jumped over the lazy dog"
    most_similair = gen.get_similair_artist(sentence, gen.artists)
    outcome = gen.get_image(sentence,most_similair)

if __name__ == '__main__':
	main()
        
    



# from sentence_transformers import SentenceTransformer, util
# from PIL import Image

# #Load CLIP model
# model = SentenceTransformer('clip-ViT-B-32')

# #Encode an image:
# img_emb = model.encode(Image.open('two_dogs_in_snow.jpeg'))

# #Encode text descriptions
# text_emb = model.encode(['Two dogs in the snow', 'A cat on a table', 'A picture of London at night'])

# #Compute cosine similarities 
# cos_scores = util.cos_sim(img_emb, text_emb)
# print(cos_scores)