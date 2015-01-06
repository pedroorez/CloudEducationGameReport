using System;
namespace Question
{
	public struct question{
		public int questionID;
		public string questiontext;
		public string alternative1;
		public string alternative2;
		public string alternative3;
		public string alternative4;
		public int rightalternative;
		
		public question(string a, string b, string c, string d, string e, string f, string g)
		{
			questionID = Convert.ToInt32(a);
			questiontext = b;
			alternative1 = c;
			alternative2 = d;
			alternative3 = e;
			alternative4 = f;
			rightalternative = Convert.ToInt32(g);
		}
	}
}

