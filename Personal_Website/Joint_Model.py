# #https://github.com/openai/CLIP
import torch
import clip
from PIL import Image
import os


class Joint_Model:
    def __init__(self):
        self.device = "cuda" if torch.cuda.is_available() else "cpu"
        self.model, self.preprocess = clip.load("ViT-B/32", device=self.device)

    def embedd_images(self,artists):
        for artist in artists:
            image_count = 0
            print('artist')
            print(artist)
            while os.path.exists('Artists/' + artist + '/image_' + str(image_count)):
                image = self.preprocess(Image.open('Artists/' + artist + '/image_' + str(image_count))).unsqueeze(0).to(self.device)
                with torch.no_grad():
                    if image_count == 0:
                        image_features = self.model.encode_image(image)
                        print(image_features.shape)
                    else:
                        image_features += self.model.encode_image(image)
                image_count +=1
                print(image_count)
            image_features = image_features / image_count
            torch.save(image_features, 'Artists/Embeddings/' + artist + '.pt')

    def check_similairity(self,sentence, artists):
        text = clip.tokenize([sentence]).to(self.device)
        cos = torch.nn.CosineSimilarity()

        max_cos = 0
        most_similair_artist = None
        with torch.no_grad():
            text_features = self.model.encode_text(text)
            logit_list = None
            for artist in artists:
                image_features = torch.load('Artists/Embeddings/' + artist + '.pt')
                sim = cos(image_features, text_features)
                if sim > max_cos:
                    max_cos = sim
                    most_similair_artist = artist
        return most_similair_artist